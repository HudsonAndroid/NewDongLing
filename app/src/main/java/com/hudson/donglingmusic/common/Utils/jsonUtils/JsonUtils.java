package com.hudson.donglingmusic.common.Utils.jsonUtils;

import android.util.Log;

import com.hudson.donglingmusic.common.Utils.jsonUtils.annotions.JsonIgnore;
import com.hudson.donglingmusic.common.Utils.jsonUtils.annotions.JsonKey;
import com.hudson.donglingmusic.common.Utils.jsonUtils.annotions.JsonSourceOnError;
import com.hudson.donglingmusic.common.Utils.jsonUtils.annotions.JsonValueConverter;
import com.hudson.donglingmusic.common.Utils.jsonUtils.annotions.MatchFieldName;
import com.hudson.donglingmusic.common.Utils.jsonUtils.impl.dataSetter.JsonParser;
import com.hudson.donglingmusic.common.Utils.jsonUtils.impl.valueTransfer.ValueTransfer;
import com.hudson.donglingmusic.common.Utils.jsonUtils.interfaces.IValueTransfer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * json工具
 */
public class JsonUtils {

    private final static ConcurrentHashMap<Class, List<SerializableField>> mClassFieldMap = new ConcurrentHashMap<>();

    public static JSONObject toJson(Object input) {
        if (input == null) {
            return null;
        }
        JSONObject output = new JSONObject();
        Class<?> aClass = input.getClass();
        try {
            putData(input, output, aClass,aClass);
        } catch (IllegalAccessException pE) {
            pE.printStackTrace();
        } catch (JSONException pE) {
            pE.printStackTrace();
        } catch (InstantiationException pE) {
            pE.printStackTrace();
        }
        return output;
    }

    public static String toJsonString(Object input) {
        JSONObject object = toJson(input);
        if (object != null) {
            return object.toString();
        }
        return null;
    }

    private static void putData(Object input, JSONObject pOutput, Class<?> pClass,Class oringinClass) throws IllegalAccessException, JSONException, InstantiationException {
        List<SerializableField> fields = getSerializableFields(pClass,oringinClass);
        for (SerializableField field : fields) {
            field.toJson(input, pOutput);
        }
        Class<?> superclass = pClass.getSuperclass();
        if (superclass != null && superclass != Object.class) {
            putData(input, pOutput, superclass,oringinClass);
        }
    }

    public static <T> T fromJson(Class<T> target, String json) {

        if (target == null || json == null) {
            return null;
        }
        T t = newInstance(target);
        if (t != null) {
            try {
                JSONObject object = new JSONObject(json);
                setData(target, t, object,target);
                return t;
            } catch (JSONException pE) {
                T result = writeError(target, t, json);
                if (result == null) {
                    pE.printStackTrace();
                }
                return result;
            } catch (IllegalAccessException pE) {
                pE.printStackTrace();
            } catch (InstantiationException pE) {
                pE.printStackTrace();
            }
        }
        return null;
    }

    private static <T> T writeError(Class<T> target, T t, String src) {
        JsonSourceOnError error = target.getAnnotation(JsonSourceOnError.class);
        if (error != null) {
            try {
                Field field = target.getDeclaredField(error.value());
                if (field != null) {
                    field.setAccessible(true);
                    field.set(t, src);
                    return t;
                }
            } catch (NoSuchFieldException pE) {
                pE.printStackTrace();
            } catch (IllegalAccessException pE) {
                pE.printStackTrace();
            }
        }else {
            Class<? super T> superclass = target.getSuperclass();
            if (superclass == null || superclass == Object.class) {
                return null;
            }
            return (T) writeError(superclass,t,src);
        }
        return null;
    }

    public static <T> List<T> fromJsonArray(Class<T> target, String json) {
        List<T> list = new ArrayList<>();
        if (target != null && json != null) {
            try {
                JSONArray array = new JSONArray(json);
                for (int i = 0; i < array.length(); i++) {
                    T t = fromJson(target, array.get(i).toString());
                    list.add(t);
                }
            } catch (JSONException pE) {
                pE.printStackTrace();
                T t = fromJson(target,json);
                if (t != null) {
                    list.add(t);
                }
            }
        }
        return list;
    }

    private static void setData(Class target, Object output, JSONObject inputJson,Class originClass) throws IllegalAccessException, InstantiationException, JSONException {
        List<SerializableField> fields = getSerializableFields(target,originClass);
        for (SerializableField field : fields) {
            field.fromJson(output, inputJson);
        }
        Class<?> superclass = target.getSuperclass();
        if (superclass != null && superclass != Object.class) {
            setData(superclass, output, inputJson,originClass);
        }
    }

    private static List<SerializableField> getSerializableFields(Class input,Class oringinClass) {
        List<SerializableField> serializableFields = mClassFieldMap.get(input);
        if (serializableFields != null) {
            return serializableFields;
        }
        serializableFields = new ArrayList<>();
        Field[] fields = input.getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isTransient(mod)) {
                //当前成员变量被transient修饰
                continue;
            }
            boolean isMatchFieldName = input.isAnnotationPresent(MatchFieldName.class);
            String key = null;
            JsonKey jsonKey = field.getAnnotation(JsonKey.class);
            if (jsonKey != null) {
                //使用注解指定的key
                key = jsonKey.value();
            } else {
                if (isMatchFieldName && !field.isAnnotationPresent(JsonIgnore.class) && !Modifier.isStatic(mod)) {
                    //直接使用成员变量名
                    key = field.getName();
                }
            }
            if (key != null) {
                SerializableField serializableField = new SerializableField(field, key,oringinClass);
                serializableFields.add(serializableField);
            }
        }
        mClassFieldMap.put(input, serializableFields);
        return serializableFields;
    }

    private static class SerializableField {
        private final Field mField;
        private final String mKey;
        private ValueTransfer mTransfer = ValueTransfer.getDefault();
        private Class mActualClass;
        SerializableField(Field field, String key,Class cls) {
            field.setAccessible(true);
            mField = field;
            Type type = field.getGenericType();
            if (type instanceof TypeVariable) {
                mActualClass = findTypicalClass((TypeVariable) type,cls);
            }
            mKey = key;
            JsonValueConverter converter = field.getAnnotation(JsonValueConverter.class);
            try {
                if (converter != null) {
                    Constructor<? extends IValueTransfer> constructor = converter.value().getDeclaredConstructor();
                    constructor.setAccessible(true);
                    IValueTransfer transfer = constructor.newInstance();
                    mTransfer = new ValueTransfer();
                    mTransfer.replace(mField.getType(), transfer);
                }
            } catch (InstantiationException pE) {
                pE.printStackTrace();
            } catch (IllegalAccessException pE) {
                pE.printStackTrace();
            } catch (NoSuchMethodException pE) {
                pE.printStackTrace();
            } catch (InvocationTargetException pE) {
                pE.printStackTrace();
            }
        }

        void toJson(Object input, JSONObject pOutput) throws IllegalAccessException, JSONException, InstantiationException {
            Object value = mField.get(input);
            if (value != null) {
                JsonParser.getInstance().toJson(value, pOutput, mKey, mTransfer);
            }
        }

        void fromJson(Object output, JSONObject inputJson) throws IllegalAccessException, JSONException, InstantiationException {
            Object value = inputJson.opt(mKey);
            if (value != null) {
                JsonParser.getInstance().fromJson(mField, output, value, mTransfer,mActualClass);
            }
        }
    }

    private static Class findTypicalClass(TypeVariable type,Class orginClass){
        Class parent = orginClass.getSuperclass();
        if (parent == null) {
            return null;
        }
        TypeVariable[] parameters = parent.getTypeParameters();
        if (parameters!=null && parameters.length >0) {
            for (int i = 0; i < parameters.length; i++) {
                TypeVariable parameter = parameters[i];
                Type genericSuperclass = orginClass.getGenericSuperclass();
                if (parameter.equals(type)) {
                    Type[] typeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
                    return (Class) typeArguments[i];
                }
            }
        }
        return findTypicalClass(type,parent);
    }

    private static <T> T newInstance(Class<T> target){
        try {
            Constructor<T> constructor = target.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (IllegalAccessException pE) {
            pE.printStackTrace();
            Log.w("", "newInstance IllegalAccessException: " + target.getName() );
        } catch (InstantiationException pE) {
            pE.printStackTrace();
            Log.w("", "newInstance InstantiationException: " + target.getName() );
        } catch (NoSuchMethodException pE) {
            pE.printStackTrace();
            Log.w("", "newInstance NoSuchMethodException: " + target.getName() );
        } catch (InvocationTargetException pE) {
            pE.printStackTrace();
            Log.w("", "newInstance InvocationTargetException: " + target.getName() );
        }
        return null;
    }
}
