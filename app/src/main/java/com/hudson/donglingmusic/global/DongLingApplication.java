package com.hudson.donglingmusic.global;

import android.app.Application;
import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;

/**
 * Created by Hudson on 2019/1/5.
 */
public class DongLingApplication extends Application {
    private static Context sContext;
    private static HttpProxyCacheServer sProxy;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        sProxy = newProxy();
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

}
