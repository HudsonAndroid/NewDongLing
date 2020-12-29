package com.hudson.donglingmusic.common.Utils.asyncUtils.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Hudson on 2020/3/24.
 */
public class ThreadPools {
    private final ExecutorService mDbOperatePool;
    private final ExecutorService mNetAccessPool;
    private static ThreadPools sInstance = null;


    private ThreadPools() {
        int processorsCount = Runtime.getRuntime().availableProcessors();
        // 速度快
        mDbOperatePool = Executors.newFixedThreadPool(processorsCount);
        // 网络，较慢
        mNetAccessPool = Executors.newFixedThreadPool(processorsCount * 4);
    }

    public static ThreadPools getInstance(){
        if(sInstance == null){
            synchronized (ThreadPools.class){
                if(sInstance == null){
                    sInstance = new ThreadPools();
                }
            }
        }
        return sInstance;
    }

    public ExecutorService getDbOperatePool() {
        return mDbOperatePool;
    }

    public ExecutorService getNetAccessPool() {
        return mNetAccessPool;
    }


}
