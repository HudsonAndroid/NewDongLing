package com.hudson.donglingmusic.persistent.db.interfaces;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Hudson on 2020/2/29.
 */
public interface IWriteableDb extends IReadableDb {
    <T> boolean insertOrUpdate(@NonNull T entity);
    <T> boolean insertOrUpdate(@NonNull List<T> entities);
    <T> boolean delete(@NonNull T entity);
    <T> boolean deleteAll(@NonNull Class<T> tClass);
}
