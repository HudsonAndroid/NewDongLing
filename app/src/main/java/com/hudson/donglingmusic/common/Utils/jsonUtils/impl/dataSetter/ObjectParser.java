package com.hudson.donglingmusic.common.Utils.jsonUtils.impl.dataSetter;

import com.hudson.donglingmusic.common.Utils.jsonUtils.impl.valueTransfer.ValueTransfer;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;


public class ObjectParser extends BaseJsonParser {
    ObjectParser() {
        super(null);
    }

    @Override
    boolean doToJson(Object value, JSONObject output, String key, ValueTransfer transfer) throws IllegalAccessException, InstantiationException, JSONException {
        output.put(key,transfer.toJson(value));
        return true;
    }

    @Override
    boolean doFromJson(Field field, Object output, Object value, ValueTransfer transfer, Class pActualClass) throws IllegalAccessException, InstantiationException, JSONException {
        if (pActualClass != null) {
            field.set(output, transfer.fromJson(pActualClass,value));
        }else {
            field.set(output, transfer.fromJson(field.getType(),value));
        }
        return true;
    }
}
