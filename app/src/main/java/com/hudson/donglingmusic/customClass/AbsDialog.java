package com.hudson.donglingmusic.customClass;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

/**
 * Created by Hudson on 2019/9/10.
 */
public abstract class AbsDialog {
    private final AlertDialog mDialog;

    public AbsDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        mDialog = builder.create();
        Window window = mDialog.getWindow();
        if(window != null){
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
        mDialog.setCancelable(true);
        mDialog.setView(initView(context),0,0,0,0);
    }

    protected abstract View initView(Context context);

    public void show(){
        mDialog.show();
    }

    public void dismiss(){
        mDialog.dismiss();
    }

    public boolean isShowing(){
        return mDialog.isShowing();
    }

    public void setCancelable(boolean cancelable){
        mDialog.setCancelable(cancelable);
    }
}
