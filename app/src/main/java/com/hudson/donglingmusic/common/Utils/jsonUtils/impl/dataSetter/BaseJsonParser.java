package com.hudson.donglingmusic.common.Utils.jsonUtils.impl.dataSetter;

import com.hudson.donglingmusic.common.Utils.jsonUtils.impl.valueTransfer.ValueTransfer;
import com.hudson.donglingmusic.common.Utils.jsonUtils.interfaces.IJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

public abstract class BaseJsonParser implements IJsonParser {
    private IJsonParser mNext;

    BaseJsonParser(IJsonParser pNext) {
        mNext = pNext;
    }

    @Override
    public void fromJson(Field field, Object output, Object value, ValueTransfer transfer, Class pActualClass) throws IllegalAccessException, InstantiationException, JSONException {
        if (!doFromJson(field,output, value,transfer,pActualClass)){
            if (mNext != null){
                mNext.fromJson(field,output, value, transfer, pActualClass);
            }
        }
    }

    @Override
    public void toJson(Object value, JSONObject output, String key, ValueTransfer transfer) throws IllegalAccessException, InstantiationException, JSONException {
        if (!doToJson(value,output,key,transfer)){
            if (mNext != null){
                mNext.toJson(value,output,key, transfer);
            }
        }
    }

    abstract boolean doToJson(Object value, JSONObject output, String key, ValueTransfer transfer) throws IllegalAccessException, InstantiationException, JSONException;

    abstract boolean doFromJson(Field field, Object output, Object value, ValueTransfer transfer, Class pActualClass) throws IllegalAccessException, InstantiationException, JSONException;
}
