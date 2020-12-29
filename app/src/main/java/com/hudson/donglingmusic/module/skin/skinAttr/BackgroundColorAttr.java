package com.hudson.donglingmusic.module.skin.skinAttr;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.View;

import com.hudson.donglingmusic.common.Utils.CommonUtils;
import com.hudson.donglingmusic.module.skin.manager.SkinManager;

/**
 * Created by Hudson on 2019/2/28.
 */
public class BackgroundColorAttr extends BaseColorAttr<View> {
    private int mColor;
    private boolean hasFetchResource = false;

    public BackgroundColorAttr(String resName) {
        super(resName);
    }

    @Override
    public void changeSkin(boolean forceUpdate) throws Resources.NotFoundException {
        if(forceUpdate){
            hasFetchResource = false;
        }
        if(!hasFetchResource){
            mColor = getColor();
        }
        for (View textView : mTargetViewSet) {
            textView.setBackgroundColor(mColor);
        }
    }

    private int getColor(){
        SkinManager instance = SkinManager.getInstance();
        Resources resources = instance.getSkinResource();
        int colorId = resources.getIdentifier(mResName, getResType(), instance.getSkinPackageName());
        try{
            int color = resources.getColor(colorId);
            hasFetchResource = true;
            return color;
        }catch (Resources.NotFoundException e){
            e.printStackTrace();
            return CommonUtils.getDefaultColor(mResName);
        }
    }

    @Override
    protected void changeItemSkin(@NonNull View view) {
        if(!hasFetchResource){
            mColor = getColor();
        }
        view.setBackgroundColor(mColor);
    }

    @NonNull
    @Override
    protected String getAttributeName() {
        return "background";
    }
}
