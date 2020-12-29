package com.hudson.donglingmusic.common.Utils.jsonUtils.impl.dataSetter;

import com.hudson.donglingmusic.common.Utils.jsonUtils.impl.valueTransfer.ValueTransfer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListParser extends BaseJsonParser {
    ListParser() {
        super(new ArrayParser());
    }

    @Override
    boolean doToJson(Object value, JSONObject output, String key, ValueTransfer transfer) throws IllegalAccessException, InstantiationException, JSONException {
        if (List.class.isInstance(value)) {
            if (((List) value).size() == 0) {
                //没有数据 ，认为已经处理完成
                return true;
            }
            JSONArray array  = new JSONArray();
            for (Object subValue : ((List) value)) {
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
            if (length == 0){
                return true;
            }
            Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType) {
                Type rawType = ((ParameterizedType) genericType).getRawType();
                Class containerClass = ((Class) rawType);
                if (List.class.isAssignableFrom(containerClass)){
                    Class actualClass = (Class) ((ParameterizedType) genericType).getActualTypeArguments()[0];
                    List list;
                    if (List.class == containerClass){
                        list = new ArrayList();
                    }else {
                        Object container = ((Class) rawType).newInstance();
                        list = (List) container;
                    }
                    for (int i = 0; i < length; i++) {
                        Object subValue = array.get(i);
                        if (subValue == null) {
                            continue;
                        }
                        Object finalValue = transfer.fromJson(actualClass,subValue);
                        if (finalValue != null) {
                            list.add(finalValue);
                        }
                    }
                    field.set(output, list);
                    return true;
                }
            }
        }
        return false;
    }
}
