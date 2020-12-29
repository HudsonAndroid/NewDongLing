package com.hudson.donglingmusic.common.Utils.jsonUtils.impl.valueTransfer;

import com.hudson.donglingmusic.common.Utils.jsonUtils.interfaces.IValueTransfer;

public abstract class BaseValueTransfer implements IValueTransfer {
    @Override
    public Object toJson(Object input) {
        return input;
    }
}
