package com.hudson.donglingmusic.common.Utils.jsonUtils.impl.dataSetter;

import com.hudson.donglingmusic.common.Utils.jsonUtils.impl.valueTransfer.ValueTransfer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapParser extends BaseJsonParser {
    public MapParser() {
        super(new ObjectParser());
    }

    @Override
    boolean doToJson(Object value, JSONObject output, String key, ValueTransfer transfer) throws IllegalAccessException, InstantiationException, JSONException {
        if (Map.class.isInstance(value)) {
            if (((Map) value).size() == 0) {
                //没有数据 ，认为已经处理完成
                return true;
            }
            JSONArray array = new JSONArray();
            Set set = ((Map) value).entrySet();
            for (Object aSet : set) {
                Map.Entry next = (Map.Entry) aSet;
                Object subKey = transfer.toJson(next.getKey());
                Object subValue = next.getValue();
                if (subKey == null || subValue == null) {
                    continue;
                }
                JSONObject object = new JSONObject();
                object.put(subKey.toString(), transfer.toJson(subValue));
                array.put(object);
            }
            output.put(key, array);
            return true;
        }
        return false;
    }

    @Override
    boolean doFromJson(Field field, Object output, Object value, ValueTransfer transfer, Class pActualClass) throws IllegalAccessException, InstantiationException, JSONException {
        if (value instanceof JSONArray) {
            JSONArray array = (JSONArray) value;
            int length = array.length();
            if (length == 0) {
                return true;
            }
            Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType) {
                Type rawType = ((ParameterizedType) genericType).getRawType();
                Class containerClass = ((Class) rawType);
                if (Map.class.isAssignableFrom(containerClass)) {
                    Class keyClass = (Class) ((ParameterizedType) genericType).getActualTypeArguments()[0];
                    Class valueClass = (Class) ((ParameterizedType) genericType).getActualTypeArguments()[1];
                    Map map;
                    if (Map.class == containerClass) {
                        map = new HashMap();
                    } else {
                        Object container = ((Class) rawType).newInstance();
                        map = (Map) container;
                    }
                    for (int i = 0; i < length; i++) {
                        Object subValue = array.get(i);
                        if (subValue == null) {
                            continue;
                        }
                        JSONObject object = new JSONObject(subValue.toString());
                        Iterator<String> keys = object.keys();
                        String next = keys.next();
                        Object keyValue = transfer.fromJson(keyClass, next);
                        Object mapValue = transfer.fromJson(valueClass, object.opt(next));
                        if (keyValue != null) {
                            map.put(keyValue, mapValue);
                        }
                    }
                    field.set(output, map);
                    return true;
                }
            }
        }
        return false;
    }
}
