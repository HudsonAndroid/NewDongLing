package com.hudson.donglingmusic.common.Utils.networkUtils.exceptions;

/**
 * Created by Hudson on 2020/2/29.
 */
public class RequestException extends RuntimeException {
    private int mResultCode;

    public RequestException(String message) {
        super(message);
    }

    public RequestException(int resultCode,String message){
        this(message);
        mResultCode = resultCode;
    }

    public RequestException(Throwable cause) {
        super(cause);
    }

    public int getResultCode() {
        return mResultCode;
    }
}
