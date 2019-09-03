package com.hudson.donglingmusic.global;

import android.app.Application;
import android.content.Context;

/**
 * Created by Hudson on 2019/1/5.
 */
public class DongLingApplication extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getGlobalContext(){
        return sContext;
    }

}
