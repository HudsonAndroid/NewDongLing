package com.hudson.donglingmusic.module.data.fetcher;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hudson.donglingmusic.common.Utils.networkUtils.NetworkUtils;
import com.hudson.donglingmusic.controller.ModuleManager;
import com.hudson.donglingmusic.entity.LocalData;
import com.hudson.donglingmusic.module.data.requestParam.IRequestParam;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Hudson on 2020/2/27.
 */
public abstract class CommonDataFetcher<DATA> extends BaseDataFetcher<DATA> {
    protected final Class<DATA> mTargetClass;
    protected final LocalData<DATA> mLocalData;

    public CommonDataFetcher() {
        mTargetClass = getTargetClass();
        mLocalData = new LocalData<>(mTargetClass);
    }

    protected DATA fetchDataFromLocal(){
        mLocalData.setUniqueId(getUniqueTag());
        return ModuleManager.getDbModule().getCommonDb().getLocalData(mLocalData);
    }

    @Nullable
    protected DATA fetchDataFromSever(){
        DATA data = NetworkUtils.requestData(getRequestParam(), mTargetClass);
        if(data != null && isDataValid(data)){
            //缓存数据
            mLocalData.setData(data);
            ModuleManager.getDbModule().getCommonDb().insertOrUpdate(mLocalData);
            return data;
        }
        return null;
    }

    @NonNull
    protected abstract IRequestParam getRequestParam();

    @SuppressWarnings("unchecked")
    private Class<DATA> getTargetClass(){
        Type type = getClass().getGenericSuperclass();
        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        return (Class<DATA>) actualTypeArguments[0];
    }

    @NonNull
    protected abstract String getUniqueTag();

    protected boolean isDataValid(@NonNull DATA data){
        return true;
    }
}
