package com.hudson.donglingmusic.UI.View.popup;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by Hudson on 2020/3/12.
 */
public abstract class BasePopup {
    private PopupWindow mPopupWindow;

    public BasePopup(Context context){
        mPopupWindow = new PopupWindow(initView(context), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
    }

    protected abstract View initView(Context context);

    public void show(View parent){
        if(mPopupWindow != null){
            mPopupWindow.showAsDropDown(parent,0,0);
        }
    }

    public void dismiss(){
        if(mPopupWindow != null){
            mPopupWindow.dismiss();
        }
    }
}

