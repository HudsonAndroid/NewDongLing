package com.hudson.donglingmusic.global;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import com.danikula.videocache.HttpProxyCacheServer;
import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.common.Utils.ToastUtils;
import com.hudson.donglingmusic.controller.ModuleManager;
import com.hudson.donglingmusic.persistent.db.DbModuleImpl;
import com.hudson.donglingmusic.service.IPlayerController;
import com.hudson.donglingmusic.service.MusicService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hudson on 2019/1/5.
 */
public class DongLingApplication extends Application {
    private static final String TAG = "DongLingApplication";
    private static ServiceConnectionEntity sConn;
    private static IPlayerController sController;
    private static final List<ControllerInitSuccessListener> sListeners = new ArrayList<>();
    private static boolean isAppRunning = false;
    private static Context sContext;
    private static HttpProxyCacheServer sProxy;

    @Override
    public Resources getResources() {
        return super.getResources();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bindService(this);
        sContext = getApplicationContext();
        sProxy = newProxy();
        isAppRunning = true;
    }

    public static Context getGlobalContext(){
        return sContext;
    }

    public static HttpProxyCacheServer getProxy(){
        return sProxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer(this);
    }

    public static void addPlayerControllerInitListener(@NonNull ControllerInitSuccessListener listener){
        if(!sListeners.contains(listener)){
            sListeners.add(listener);
            if(sController != null){
                listener.onPlayerControllerInitSuccess(sController);
            }
        }
    }

    public static void removePlayerControllerInitListener(@NonNull ControllerInitSuccessListener listener){
        sListeners.remove(listener);
    }

    public static IPlayerController getPlayerController(){
        if(sController == null){
            throw new IllegalStateException("the player service does not bind,you can not use it");
        }
        return sController;
    }

    private static void bindService(Context context){
        Intent intent = new Intent(context, MusicService.class);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //android8.0以上通过startForegroundService启动service
            context.startForegroundService(intent);
        }else{
            context.startService(intent);
        }
        sConn = new ServiceConnectionEntity();
        context.bindService(intent, sConn,BIND_AUTO_CREATE);
    }

    public static void exit(){
        isAppRunning = false;
        getGlobalContext().unbindService(sConn);
        ((DbModuleImpl) ModuleManager.getDbModule()).close();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private static class ServiceConnectionEntity implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG,"service connect successfully");
            sController = (IPlayerController) service;
            for (ControllerInitSuccessListener listener : sListeners) {
                listener.onPlayerControllerInitSuccess(sController);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG,"service disconnected");
            if(isAppRunning){
                Log.e(TAG,"app is running,retry to bind service");
                DongLingApplication.bindService(DongLingApplication.getGlobalContext());
            }
        }

        @Override
        public void onNullBinding(ComponentName name) {
            Log.e(TAG,"bind failed");
            ToastUtils.showToast(R.string.error_bind_service);
            exit();
        }
    }
}
