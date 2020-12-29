package com.hudson.donglingmusic.common.exception;

import com.hudson.donglingmusic.common.Utils.ToastUtils;

/**
 * Created by Hudson on 2020/3/3.
 */
public abstract class AutoHandleException extends RuntimeException {

    public AutoHandleException(String message) {
        super(message);
    }

    public void handleException(){
        printStackTrace();
        ToastUtils.showToast(getMessage());
    }
}
