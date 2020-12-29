package com.hudson.donglingmusic.module.skin.manager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.common.Utils.ToastUtils;
import com.hudson.donglingmusic.common.Utils.asyncUtils.AsyncException;
import com.hudson.donglingmusic.common.Utils.asyncUtils.AsyncTask;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoInBackground;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoOnFail;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoOnSuccess;
import com.hudson.donglingmusic.global.DongLingApplication;
import com.hudson.donglingmusic.module.skin.exception.SkinEmptyException;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hudson on 2020/3/24.
 */
public class SkinManager {
    private String mSkinPackageName; // 皮肤包的包名
    private Resources mSkinResource; // 皮肤包的资源
    private static SkinManager sInstance = null;
    private final List<OnSkinLoadCompleteListener> mSkinLoadCompleteListeners;
    private boolean isUseDefault = true;

    private SkinManager(){
        useDefault();
        mSkinLoadCompleteListeners = new ArrayList<>();
    }

    public static SkinManager getInstance(){
        if(sInstance == null){
            synchronized (SkinManager.class){
                if(sInstance == null){
                    sInstance = new SkinManager();
                }
            }
        }
        return sInstance;
    }

    public void useDefault(){
        Context globalContext = DongLingApplication.getGlobalContext();
        mSkinPackageName = globalContext.getPackageName();
        mSkinResource = globalContext.getResources();
        isUseDefault = true;
    }

    public void loadSkin(@NonNull final String path) throws SkinEmptyException{
        File skinFile = new File(path);
        if(!skinFile.exists()){
            throw new SkinEmptyException();
        }
        new AsyncTask<Resources>().doInBackground(new IDoInBackground<Resources>() {
            @Override
            public Resources run() throws AsyncException {
                try {
                    AssetManager assetManager = AssetManager.class.newInstance();
                    Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                    addAssetPath.invoke(assetManager,path);
                    Resources resources = DongLingApplication.getGlobalContext().getResources();
                    return new Resources(assetManager, resources.getDisplayMetrics(),
                            resources.getConfiguration());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).doOnSuccess(new IDoOnSuccess<Resources>() {
            @Override
            public void onSuccess(Resources resources) {
                PackageInfo info = DongLingApplication.getGlobalContext()
                        .getPackageManager().getPackageArchiveInfo(path,
                                PackageManager.GET_ACTIVITIES);
                mSkinPackageName = info.packageName;
                mSkinResource = resources;
                notifyChangeSkin();
                isUseDefault = false;
                Log.e("hudson","资源加载成功");
            }
        }).doOnFail(new IDoOnFail() {
            @Override
            public void onFail(Throwable e) {
                e.printStackTrace();
                ToastUtils.showToast(R.string.common_skin_resource_load_failed);
                useDefault();
            }
        }).useDbThreadPool().execute();
    }

    public String getSkinPackageName() {
        return mSkinPackageName;
    }

    public Resources getSkinResource() {
        return mSkinResource;
    }

    public boolean isUseDefault() {
        return isUseDefault;
    }

    private void notifyChangeSkin(){
        for (OnSkinLoadCompleteListener skinLoadCompleteListener : mSkinLoadCompleteListeners) {
            skinLoadCompleteListener.onSkinLoadComplete();
        }
    }

    public void addSkinLoadCompleteListener(@NonNull OnSkinLoadCompleteListener listener){
        if(!mSkinLoadCompleteListeners.contains(listener)){
            mSkinLoadCompleteListeners.add(listener);
        }
    }

    public void removeSkinLoadCompleteListener(@NonNull OnSkinLoadCompleteListener listener){
        mSkinLoadCompleteListeners.remove(listener);
    }
}
