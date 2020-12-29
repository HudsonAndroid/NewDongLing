package com.hudson.donglingmusic.UI.View;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

/**
 * 自定义RadioButton（没有原生的勾选框）
 * Created by Hudson on 2018/4/19.
 */

public class BaseRadioButton<T> extends AppCompatRadioButton {
    protected T mExtraValue;

    public BaseRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRadioButton(Context context, int descId) {
        super(context);
        setText(descId);
        setButtonDrawable(null);
    }

    public BaseRadioButton(Context context, String desc){
        super(context);
        setText(desc);
        setButtonDrawable(null);
    }

    public BaseRadioButton<T> setViewId(int id){
        setId(id);
        return this;
    }

    public BaseRadioButton<T> setViewChecked(boolean checked){
        setChecked(checked);
        return this;
    }

    public BaseRadioButton<T> setValue(T value){
        mExtraValue = value;
        return this;
    }

    public T getValue(){
        return mExtraValue;
    }
}
