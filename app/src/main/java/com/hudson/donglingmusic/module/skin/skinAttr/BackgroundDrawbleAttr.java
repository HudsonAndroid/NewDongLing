package com.hudson.donglingmusic.module.skin.skinAttr;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.hudson.donglingmusic.global.DongLingApplication;
import com.hudson.donglingmusic.module.skin.manager.SkinManager;

/**
 * Created by Hudson on 2020/3/25.
 */
public class BackgroundDrawbleAttr extends BaseDrawableAttr<View> {

    @NonNull
    @Override
    protected String getAttributeName() {
        return "background";
    }

    public BackgroundDrawbleAttr(String resName) {
        super(resName);
    }

    @Override
    public void changeSkin(boolean forceUpdate) {
        SkinManager instance = SkinManager.getInstance();
        Resources resources = instance.getSkinResource();
        int drawableId = resources.getIdentifier(mResName, getResType(), instance.getSkinPackageName());
        try{
            Log.e("hudson","更换皮肤"+drawableId);
            Drawable result = resources.getDrawable(drawableId, DongLingApplication.getGlobalContext().getTheme());
            for (View view : mTargetViewSet) {
                view.setBackground(result);
            }
            Log.e("hudson","更换成功");
        }catch (Resources.NotFoundException e){
            Log.e("hudson","更换失败");
            e.printStackTrace();
        }
    }

    @Override
    protected void changeItemSkin(@NonNull View view) {
        SkinManager instance = SkinManager.getInstance();
        Resources resources = instance.getSkinResource();
        int drawableId = resources.getIdentifier(mResName, getResType(), instance.getSkinPackageName());
        try{
            Log.e("hudson","我这边更换皮肤"+drawableId);
            Drawable result = resources.getDrawable(drawableId, DongLingApplication.getGlobalContext().getTheme());
//            for (View view : mTargetViewSet) {
                view.setBackground(result);
//            }
            Log.e("hudson","我这边更换成功");
        }catch (Resources.NotFoundException e){
            Log.e("hudson","我这边更换失败");
            e.printStackTrace();
        }
    }
}
