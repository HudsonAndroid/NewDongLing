package com.hudson.donglingmusic.module.skin.skinAttr;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.hudson.donglingmusic.global.DongLingApplication;
import com.hudson.donglingmusic.module.skin.manager.SkinManager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用处理换肤，仅针对通用属性很多的view；如果目标不是
 * 很通用或者特例，请使用SkinManager来监听皮肤包的加载
 * Created by Hudson on 2019/2/28.
 */
public abstract class AbsSkinAttr<TargetView extends View> {
    protected final List<TargetView> mTargetViewSet = new ArrayList<>();
    private final int mResId;
    protected final String mResName;
    protected SkinManager mSkinManager;

    public AbsSkinAttr(String resName) {
        mSkinManager = SkinManager.getInstance();
        mResName = resName;
        Context context = DongLingApplication.getGlobalContext();
        mResId = context.getResources().getIdentifier(resName, getResType(), context.getPackageName());
//        Log.e("hudson2","实际的id是"+(R.color.common_light_white));
    }

    @NonNull
    protected abstract String getResType();

    public abstract void changeSkin(boolean forceUpdate);

    @SuppressWarnings("unchecked")
    public void checkViewAttr(@NonNull View v,@NonNull AttributeSet attrs){
        if(needCheck(v)){
            int attributeCount = attrs.getAttributeCount();
            for (int i = 0; i < attributeCount; i++) {
                // 获取的属性值，引用类型是 "@数字"
                String attributeValue = attrs.getAttributeValue(i);
                String attributeName = attrs.getAttributeName(i);
                if(attributeValue.startsWith("@") && getAttributeName().equals(attributeName)){//如果是引用类型
//                    Log.e("hudson","属性值"+attributeValue+"属性名"+attributeName);
                    int resId = Integer.parseInt(attributeValue.substring(1));
//                    Log.e("hudson","固定的"+mResId+"当前"+resId);
                    if(mResId == resId){
//                        Log.e("hudson","找到了");
                        TargetView target = (TargetView) v;
                        mTargetViewSet.add(target);
                        if(!mSkinManager.isUseDefault()){
                            changeItemSkin(target);
                        }
                        break;
                    }
                }
            }
        }
    }

    @NonNull
    protected abstract String getAttributeName();

    protected abstract void changeItemSkin(@NonNull TargetView view);

    private boolean needCheck(@NonNull View v){
        Class<TargetView> targetClass = getTargetClass();
        Class<? extends View> aClass = v.getClass();
        return targetClass.isAssignableFrom(aClass);
    }

    @SuppressWarnings("unchecked")
    private Class<TargetView> getTargetClass(){
        Type type = getClass().getGenericSuperclass();
        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        return (Class<TargetView>) actualTypeArguments[0];
    }
}
