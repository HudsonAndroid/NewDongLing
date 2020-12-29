package com.hudson.donglingmusic.common.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.hudson.donglingmusic.common.Utils.CommonUtils;

/**
 * Created by Hudson on 2019/11/10.
 */
public class ConfigManager {
    private SharedPreferences mSp;
    private SharedPreferences.Editor mEditor;
    private volatile static ConfigManager sInstance = null;

    private ConfigManager(Context context) {
        mSp = context.getSharedPreferences("donglingMusic",
                Context.MODE_PRIVATE);
        mEditor = mSp.edit();
    }

    public static ConfigManager getInstance(){
        if(sInstance == null){
            synchronized (ConfigManager.class) {
                if(sInstance ==null){
                    sInstance = new ConfigManager(CommonUtils.getContext());
                }
            }
        }
        return sInstance;
    }

    public void putBoolean(String key,boolean result){
        mEditor.putBoolean(key, result);
        mEditor.apply();
    }

    public boolean getBoolean(String key,boolean defaultValue){
        return mSp.getBoolean(key,defaultValue);
    }

    public void putInt(String key,int value){
        mEditor.putInt(key, value);
        mEditor.apply();
    }

    public int getInt(String key,int defaultValue){
        return mSp.getInt(key,defaultValue);
    }
}
