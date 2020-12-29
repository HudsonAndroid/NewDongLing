package com.hudson.donglingplayer.exception;

/**
 * Created by hudson on 2019/8/29.
 */
public class UnSupportOperationException extends RuntimeException{

    public UnSupportOperationException() {
        super("Sorry,this operation doesn't support!");
    }

    public UnSupportOperationException(String extendTip) {
        super("Sorry,this operation doesn't support!"+extendTip);
    }
}
