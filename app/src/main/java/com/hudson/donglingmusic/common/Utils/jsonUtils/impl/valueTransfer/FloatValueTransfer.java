package com.hudson.donglingmusic.common.Utils.jsonUtils.impl.valueTransfer;

public class FloatValueTransfer extends BaseValueTransfer {
    @Override
    public Object fromJson(Object value) {
        if (value == null) {
            return 0;
        }
        try {
            return Float.valueOf(value.toString());
        } catch (NumberFormatException pE) {
            return 0;
        }
    }
}
