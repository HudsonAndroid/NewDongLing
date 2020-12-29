package com.hudson.donglingmusic.common.Utils.jsonUtils.impl.valueTransfer;

public class DoubleValueTransfer extends BaseValueTransfer {
    @Override
    public Object fromJson(Object value) {
        if (value == null) {
            return 0;
        }
        try {
            return Double.valueOf(value.toString());
        } catch (NumberFormatException pE) {
            return 0;
        }
    }
}
