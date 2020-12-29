package com.hudson.donglingmusic.common.Utils.jsonUtils.impl.dataSetter;

import com.hudson.donglingmusic.common.Utils.jsonUtils.impl.valueTransfer.ValueTransfer;
import com.hudson.donglingmusic.common.Utils.jsonUtils.interfaces.IJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;


public class JsonParser implements IJsonParser {
    private static JsonParser mInstance = new JsonParser();

    public static JsonParser getInstance() {
        return mInstance;
    }

    private IJsonParser mParser = new ListParser();

    public void fromJson(Field field, Object output, Object value, ValueTransfer transfer, Class pActualClass) throws IllegalAccessException, InstantiationException, JSONException{
        mParser.fromJson(field,output,value, transfer, pActualClass);
    }

    public void toJson(Object value, JSONObject output, String key, ValueTransfer transfer) throws IllegalAccessException, JSONException, InstantiationException {
        mParser.toJson(value,output,key, transfer);
    }

}
