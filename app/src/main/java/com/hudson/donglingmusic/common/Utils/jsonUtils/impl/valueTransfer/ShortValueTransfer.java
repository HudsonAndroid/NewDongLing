package com.hudson.donglingmusic.common.Utils.jsonUtils.impl.valueTransfer;

public class ShortValueTransfer extends BaseValueTransfer {
    @Override
    public Object fromJson(Object value) {
        if (value == null) {
            return new Short("0");
        }
        try {
            return Short.valueOf(value.toString());
        } catch (NumberFormatException pE) {
            return new Short("0");
        }
    }
}
