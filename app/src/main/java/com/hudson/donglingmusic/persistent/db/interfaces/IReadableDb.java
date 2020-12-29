package com.hudson.donglingmusic.persistent.db.interfaces;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Hudson on 2020/2/29.
 */
public interface IReadableDb {
    @NonNull
    <T> List<T> getAllData(@NonNull Class<T> tClass);
    void close();
}
