package com.hudson.donglingmusic.module.skin.skinAttr;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by Hudson on 2020/3/25.
 */
public abstract class BaseDrawableAttr<TargetView extends View> extends AbsSkinAttr<TargetView> {
    public BaseDrawableAttr(String resName) {
        super(resName);
    }

    @NonNull
    @Override
    protected String getResType() {
        return "drawable";
    }
}
