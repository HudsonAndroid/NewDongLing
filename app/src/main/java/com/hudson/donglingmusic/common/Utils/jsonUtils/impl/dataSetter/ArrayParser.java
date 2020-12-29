package com.hudson.donglingmusic.common.Utils.jsonUtils.impl.dataSetter;

import com.hudson.donglingmusic.common.Utils.jsonUtils.impl.valueTransfer.ValueTransfer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class ArrayParser extends BaseJsonParser {

    public ArrayParser() {
        super(new MapParser());
    }

    @Override
    boolean doToJson(Object value, JSONObject output, String key, ValueTransfer transfer) throws IllegalAccessException, InstantiationException, JSONException {
        if (value.getClass().isArray()) {
            int length = Array.getLength(value);
            if (length == 0) {
                //没有数据，认为已经处理完成
                return true;
            }
            JSONArray array  = new JSONArray();
            for (int i = 0; i < length; i++) {
                Object subValue = Array.get(value, i);
                if (subValue == null) {
                    continue;
                }
                array.put(transfer.toJson(subValue));
            }
            output.put(key,array);
            return true;
        }
        return false;
    }

    @Override
    public boolean doFromJson(Field field, Object output, Object value, ValueTransfer transfer, Class pActualClass) throws IllegalAccessException, InstantiationException, JSONException {
        if (value instanceof JSONArray) {
            JSONArray array = (JSONArray) value;
            int length = array.length();
            if (length == 0) {
                return true;
            }
            Type genericType = field.getGenericType();
            if (genericType instanceof Class) {
                if (((Class) genericType).isArray()) {
                    Class componentType = ((Class) genericType).getComponentType();
                    Object container = Array.newInstance(componentType, length);
                    for (int i = 0; i < length; i++) {
                        Object subValue = array.get(i);
                        if (subValue == null) {
                            continue;
                        }
                        Object finalValue = transfer.fromJson(componentType,subValue);
                        if (finalValue != null) {
                            Array.set(container, i, finalValue);
                        }
                    }
                    field.set(output, container);
                    return true;
                }
            }
        }
        return false;
    }
}
