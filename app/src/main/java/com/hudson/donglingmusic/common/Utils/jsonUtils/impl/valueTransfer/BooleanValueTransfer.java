package com.hudson.donglingmusic.common.Utils.jsonUtils.impl.valueTransfer;


public class BooleanValueTransfer extends BaseValueTransfer {
    @Override
    public Object fromJson(Object value) {
        if (value == null) {
            return false;
        }
        if (value.toString().equalsIgnoreCase("true")) {
            return true;
        }
        if (value.toString().equalsIgnoreCase("false")) {
            return false;
        }
        try {
            if (Integer.parseInt(value.toString()) == 1) {
                return true;
            }
        } catch (NumberFormatException pE) {
            pE.printStackTrace();
        }
        return false;
    }
}
