package com.hudson.donglingmusic.common.Utils.jsonUtils.impl.valueTransfer;

public class StringValueTransfer extends BaseValueTransfer {
    @Override
    public Object fromJson(Object value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }
}
