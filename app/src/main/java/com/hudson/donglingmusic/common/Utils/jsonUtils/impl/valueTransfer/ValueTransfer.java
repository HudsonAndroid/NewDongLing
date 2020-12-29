package com.hudson.donglingmusic.common.Utils.jsonUtils.impl.valueTransfer;

import com.hudson.donglingmusic.common.Utils.jsonUtils.JsonUtils;
import com.hudson.donglingmusic.common.Utils.jsonUtils.interfaces.IValueTransfer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ValueTransfer {

    private Map<Class, IValueTransfer> mValueTransferMap = new HashMap<>();
    private static ValueTransfer mDefault = new ValueTransfer();

    public ValueTransfer() {
        IntegerValueTransfer integerValueTransfer = new IntegerValueTransfer();
        mValueTransferMap.put(int.class, integerValueTransfer);
        mValueTransferMap.put(Integer.class, integerValueTransfer);

        ShortValueTransfer shortValueTransfer = new ShortValueTransfer();
        mValueTransferMap.put(short.class, shortValueTransfer);
        mValueTransferMap.put(Short.class, shortValueTransfer);

        LongValueTransfer longValueTransfer = new LongValueTransfer();
        mValueTransferMap.put(long.class, longValueTransfer);
        mValueTransferMap.put(Long.class,new LongValueTransfer());

        DoubleValueTransfer doubleValueTransfer = new DoubleValueTransfer();
        mValueTransferMap.put(double.class, doubleValueTransfer);
        mValueTransferMap.put(Double.class, doubleValueTransfer);

        FloatValueTransfer floatValueTransfer = new FloatValueTransfer();
        mValueTransferMap.put(float.class, floatValueTransfer);
        mValueTransferMap.put(Float.class, floatValueTransfer);

        BooleanValueTransfer booleanValueTransfer = new BooleanValueTransfer();
        mValueTransferMap.put(boolean.class, booleanValueTransfer);
        mValueTransferMap.put(Boolean.class, booleanValueTransfer);

        mValueTransferMap.put(String.class,new StringValueTransfer());
    }

    public void replace(Class key,IValueTransfer pTransfer){
        IValueTransfer transfer = mValueTransferMap.get(key);
        if (transfer == null) {
            return;
        }
        Iterator<Map.Entry<Class, IValueTransfer>> iterator = mValueTransferMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Class, IValueTransfer> entry = iterator.next();
            if (entry.getValue() == transfer) {
                entry.setValue(pTransfer);
            }
        }
    }

    private IValueTransfer getValueTransfer(Class pClass){
        if (pClass == null) {
            return null;
        }
        return mValueTransferMap.get(pClass);
    }

    public Object toJson(Object input){
        IValueTransfer valueTransfer = mValueTransferMap.get(input.getClass());
        if (valueTransfer != null) {
            return valueTransfer.toJson(input);
        }else {
            return JsonUtils.toJson(input);
        }
    }

    public Object fromJson(Class keyClass, Object subValue){
        IValueTransfer valueTransfer = getValueTransfer(keyClass);
        if (valueTransfer != null) {
            return valueTransfer.fromJson(subValue);
        }else {
            return JsonUtils.fromJson(keyClass, subValue.toString());
        }
    }

    public static ValueTransfer getDefault(){
        return mDefault;
    }
}
