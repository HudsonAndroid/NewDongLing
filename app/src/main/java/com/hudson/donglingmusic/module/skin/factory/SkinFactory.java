package com.hudson.donglingmusic.module.skin.factory;

import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.hudson.donglingmusic.module.skin.manager.PageSkinManager;
import com.hudson.donglingmusic.module.skin.skinAttr.AbsSkinAttr;

/**
 * 控件工厂
 * Created by Hudson on 2019/2/28.
 */
public class SkinFactory implements LayoutInflater.Factory2 {
    private PageSkinManager mSkinManager;
    private AppCompatDelegate mDelegate;

    public SkinFactory(AppCompatDelegate delegate) {
        mSkinManager = new PageSkinManager();
        mDelegate = delegate;
    }

    public SkinFactory addSkinAttr(AbsSkinAttr skinAttr){
        mSkinManager.addSkinAttr(skinAttr);
        return this;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//        View view = mDelegate.createView(parent, name, context, attrs);
//        if(view != null && attrs != null){
//            // 由于当前view还没有跟属性值对应上，因此需要自行解析attrs
//            mSkinManager.collectViews(view,attrs);
//        }
//        return view;
        return onCreateView(name,context,attrs);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
//        return onCreateView(null,name,context,attrs);
        View view = null;
        try {
            if (-1 == name.indexOf('.')){   //不带".",说明是系统的View
                if ("View".equals(name)) {
                    view = LayoutInflater.from(context).createView(name, "android.view.", attrs);
                }
                if (view == null) {
                    view = LayoutInflater.from(context).createView(name, "android.widget.", attrs);
                }
                if (view == null) {
                    view = LayoutInflater.from(context).createView(name, "android.webkit.", attrs);
                }
            }else { //带".",说明是自定义的View
                view = LayoutInflater.from(context).createView(name, null, attrs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            view = null;
        }
        if(view != null && attrs != null){
            // 由于当前view还没有跟属性值对应上，因此需要自行解析attrs
            mSkinManager.collectViews(view,attrs);
        }
        return view;
    }

    public void onDestroy(){
        mSkinManager.onDestroy();
    }
}
