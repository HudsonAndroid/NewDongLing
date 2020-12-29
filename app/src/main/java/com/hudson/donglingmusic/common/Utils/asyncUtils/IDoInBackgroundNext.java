package com.hudson.donglingmusic.common.Utils.asyncUtils;

public interface IDoInBackgroundNext<T> {
    void run(T t) throws AsyncException;
}
