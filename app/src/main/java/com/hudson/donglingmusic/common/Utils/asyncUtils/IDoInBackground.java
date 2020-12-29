package com.hudson.donglingmusic.common.Utils.asyncUtils;

public interface IDoInBackground<T> {
    T run() throws AsyncException;
}
