package com.hudson.donglingmusic.UI.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

import com.hudson.donglingmusic.UI.View.TabView.RadioGroup.inner.BaseRadioButton;


/**
 * 自定义RadioGroup，可以获取到checked的radioButton
 * Created by hpz on 2018/4/19.
 */
public class MyRadioGroup<T extends BaseRadioButton> extends RadioGroup {

    public MyRadioGroup(Context context) {
        this(context, null);
    }

    public MyRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressWarnings("unchecked")
    public  T getCheckedRadioButton(){
        int checkedRadioButtonId = getCheckedRadioButtonId();
        return (T) findViewById(checkedRadioButtonId);
    }

}
