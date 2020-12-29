package com.hudson.donglingmusic.persistent.db.impl;

import android.support.annotation.NonNull;

import com.hudson.donglingmusic.persistent.db.BaseDb;
import com.hudson.donglingmusic.persistent.db.interfaces.IReadableDb;

import java.util.List;

/**
 * Created by Hudson on 2020/2/29.
 */
public class BaseReadableDb<DB extends BaseDb> implements IReadableDb {
    protected DB mDB;

    public BaseReadableDb(DB DB) {
        mDB = DB;
    }

    @NonNull
    @Override
    public <T> List<T> getAllData(@NonNull Class<T> tClass) {
        return mDB.queryAll(tClass);
    }

    @Override
    public void close() {
        mDB.close();
    }
}
