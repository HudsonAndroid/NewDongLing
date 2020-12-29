package com.hudson.donglingmusic.module.data.fetcher;

import android.support.annotation.Nullable;

import com.hudson.donglingmusic.module.data.interfaces.IDataFetcher;

/**
 * Created by Hudson on 2020/3/8.
 */
public abstract class BaseDataFetcher<DATA> implements IDataFetcher<DATA> {
    private boolean mForceServer = false;

    public BaseDataFetcher<DATA> setForceServer(boolean forceServer) {
        mForceServer = forceServer;
        return this;
    }

    @Override
    public final DATA fetchData() {
        if(mForceServer){
            return fetchDataFromSever();
        }
        DATA data = fetchDataFromLocal();
        if (data == null) {
            return fetchDataFromSever();
        }
        return data;
    }

    protected abstract DATA fetchDataFromLocal();

    @Nullable
    protected abstract DATA fetchDataFromSever();
}
