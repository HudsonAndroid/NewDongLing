package com.hudson.donglingmusic.common.Utils.asyncUtils;

public class AsyncException extends RuntimeException{
    public AsyncException(String message) {
        super(message);
    }

    public AsyncException(Throwable cause) {
        super(cause);
    }
}
