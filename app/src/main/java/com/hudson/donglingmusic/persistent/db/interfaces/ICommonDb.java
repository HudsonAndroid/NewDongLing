package com.hudson.donglingmusic.persistent.db.interfaces;

import android.support.annotation.NonNull;

import com.hudson.donglingmusic.entity.LocalData;

/**
 * Created by Hudson on 2020/2/29.
 */
public interface ICommonDb extends IWriteableDb {

    <T> T getLocalData(@NonNull LocalData<T> localData);
}
