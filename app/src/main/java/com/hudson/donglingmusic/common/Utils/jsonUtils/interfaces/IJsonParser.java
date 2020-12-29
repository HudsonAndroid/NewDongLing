package com.hudson.donglingmusic.common.Utils.jsonUtils.interfaces;

import com.hudson.donglingmusic.common.Utils.jsonUtils.impl.valueTransfer.ValueTransfer;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

public interface IJsonParser {
    void fromJson(Field field, Object output, Object value, ValueTransfer transfer, Class pActualClass) throws IllegalAccessException, InstantiationException, JSONException;

    void toJson(Object value, JSONObject output, String key, ValueTransfer transfer) throws IllegalAccessException, InstantiationException, JSONException;
}
