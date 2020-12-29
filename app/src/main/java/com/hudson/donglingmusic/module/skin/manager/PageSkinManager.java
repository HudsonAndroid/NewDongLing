package com.hudson.donglingmusic.module.skin.manager;

import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.hudson.donglingmusic.module.skin.skinAttr.AbsSkinAttr;

import java.util.ArrayList;
import java.util.List;

/**
 * 本类不直接继承SkinManager，避免单例持有view导致内存泄漏
 * Created by Hudson on 2020/3/25.
 */
public class PageSkinManager implements OnSkinLoadCompleteListener {
    private final List<AbsSkinAttr> mSkinAttrs = new ArrayList<>();
    private final SkinManager mSkinManager;

    public PageSkinManager() {
        mSkinManager = SkinManager.getInstance();
        mSkinManager.addSkinLoadCompleteListener(this);
    }

    public void addSkinAttr(AbsSkinAttr skinAttr){
        mSkinAttrs.add(skinAttr);
    }

    public void collectViews(@NonNull View v, AttributeSet attrs){
        for (AbsSkinAttr skinAttr : mSkinAttrs) {
            skinAttr.checkViewAttr(v,attrs);
        }
    }

    @Override
    public void onSkinLoadComplete() {
        for (AbsSkinAttr skinAttr : mSkinAttrs) {
            skinAttr.changeSkin(true);
        }
    }

    public void onDestroy(){
        mSkinManager.removeSkinLoadCompleteListener(this);
    }
}
