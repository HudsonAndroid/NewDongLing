package com.hudson.donglingmusic.persistent.db.impl;

import android.support.annotation.NonNull;

import com.hudson.donglingmusic.persistent.db.BaseDb;
import com.hudson.donglingmusic.persistent.db.interfaces.IWriteableDb;

import java.util.List;

/**
 * Created by Hudson on 2020/2/29.
 */
public class BaseWriteableDb<DB extends BaseDb> extends BaseReadableDb<DB> implements IWriteableDb {

    public BaseWriteableDb(DB DB) {
        super(DB);
    }

    @Override
    public <T> boolean insertOrUpdate(@NonNull T entity) {
        return mDB.insertOrUpdate(entity);
    }

    @Override
    public <T> boolean insertOrUpdate(@NonNull List<T> entities) {
        return mDB.insertOrUpdate(entities);
    }

    @Override
    public <T> boolean delete(@NonNull T entity) {
        return mDB.delete(entity);
    }

    @Override
    public <T> boolean deleteAll(@NonNull Class<T> tClass) {
        return mDB.deleteAll(tClass);
    }
}
