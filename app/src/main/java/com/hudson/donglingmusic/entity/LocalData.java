package com.hudson.donglingmusic.entity;

import com.hudson.donglingmusic.common.Utils.jsonUtils.JsonUtils;
import com.hudson.donglingmusic.controller.ModuleManager;
import com.hudson.donglingmusic.persistent.LocalDataValidManager;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Hudson on 2020/2/27.
 */
@Entity
public class LocalData<T> {
    public static final String UNIQUE_ID = "unique_tag";

    @Unique
    @Property(nameInDb = UNIQUE_ID)
    private String mUniqueId;

    private String mJsonStr;

    private String mDataType;

    @Transient
    private Class<T> mTargetClass;

    private long mExpireTime;

    public LocalData(Class<T> targetClass){
        setTargetClass(targetClass);
    }

    @Generated(hash = 646281666)
    public LocalData(String mUniqueId, String mJsonStr, String mDataType, long mExpireTime) {
        this.mUniqueId = mUniqueId;
        this.mJsonStr = mJsonStr;
        this.mDataType = mDataType;
        this.mExpireTime = mExpireTime;
    }

    @Generated(hash = 643088711)
    public LocalData() {
    }

    public void setTargetClass(Class<T> targetClass){
        mTargetClass = targetClass;
        mDataType = targetClass.getSimpleName();
        mUniqueId = mDataType + ModuleManager.getLanguageModule().getLanguageString();
        mExpireTime = LocalDataValidManager.getExpireTime(targetClass,System.currentTimeMillis());
    }

    public void setUniqueId(String uniqueId) {
        mUniqueId = mDataType + "_" + uniqueId + ModuleManager.getLanguageModule().getLanguageString();
    }

    public String getData() {
        return mJsonStr;
    }

    public String getUniqueId() {
        return mUniqueId;
    }

    public void setData(Object object){
        if (object != null) {
            if (object instanceof String) {
                mJsonStr = object.toString();
            }else {
                mJsonStr = JsonUtils.toJsonString(object);
            }
        }
    }

    public Class<T> getTargetClass() {
        return mTargetClass;
    }

    public long getExpireTime() {
        return mExpireTime;
    }

    public String getMUniqueId() {
        return this.mUniqueId;
    }

    public void setMUniqueId(String mUniqueId) {
        this.mUniqueId = mUniqueId;
    }

    public String getMJsonStr() {
        return this.mJsonStr;
    }

    public void setMJsonStr(String mJsonStr) {
        this.mJsonStr = mJsonStr;
    }

    public String getMDataType() {
        return this.mDataType;
    }

    public void setMDataType(String mDataType) {
        this.mDataType = mDataType;
    }

    public long getMExpireTime() {
        return this.mExpireTime;
    }

    public void setMExpireTime(long mExpireTime) {
        this.mExpireTime = mExpireTime;
    }

    @Override
    public String toString() {
        return "LocalData{" +
                "mUniqueId='" + mUniqueId + '\'' +
                ", mJsonStr='" + mJsonStr + '\'' +
                ", mDataType='" + mDataType + '\'' +
                ", mTargetClass=" + mTargetClass +
                ", mExpireTime=" + mExpireTime +
                '}';
    }
}
