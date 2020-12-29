package com.hudson.donglingmusic.common.Utils;

import android.widget.Toast;

/**
 * Created by Hudson on 2017/3/19.
 * 让整体只有一个Toast
 */

public class ToastUtils {

    private static Toast mToast = Toast.makeText(CommonUtils.getContext(),"", Toast.LENGTH_SHORT);

    public static void showToast(String info){
        mToast.setText(info);
        mToast.show();
    }

    public static void showToast(int strId){
        mToast.setText(strId);
        mToast.show();
    }

}