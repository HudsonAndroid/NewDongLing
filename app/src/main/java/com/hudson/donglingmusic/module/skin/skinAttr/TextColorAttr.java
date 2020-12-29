package com.hudson.donglingmusic.module.skin.skinAttr;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.hudson.donglingmusic.common.Utils.CommonUtils;
import com.hudson.donglingmusic.module.skin.manager.SkinManager;

/**
 * Created by Hudson on 2020/3/25.
 */
public class TextColorAttr extends BaseColorAttr<TextView> {
    private int mColor;
    private boolean hasFetchResource = false;

    public TextColorAttr(String resName) {
        super(resName);
    }

    @Override
    public void changeSkin(boolean forceUpdate) {
        if(forceUpdate){
            hasFetchResource = false;
        }
        if(!hasFetchResource){
            mColor = getColor();
        }
        for (TextView textView : mTargetViewSet) {
            textView.setTextColor(mColor);
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
    protected void changeItemSkin(@NonNull TextView view) {
        if(!hasFetchResource){
            mColor = getColor();
        }
        view.setTextColor(mColor);
    }

    @NonNull
    @Override
    protected String getAttributeName() {
        return "textColor";
    }
}
