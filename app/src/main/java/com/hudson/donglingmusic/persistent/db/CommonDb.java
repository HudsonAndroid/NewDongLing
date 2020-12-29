package com.hudson.donglingmusic.persistent.db;

/**
 *
 * Created by Hudson on 2020/2/29.
 */
public class CommonDb extends BaseDb {
    private static final String DB_NAME = "common.db";

    private static CommonDb sInstance;

    private CommonDb() {
        super(DB_NAME);
    }

    public static CommonDb getInstance(){
        if(sInstance == null){
            synchronized (CommonDb.class){
                if(sInstance == null){
                    sInstance = new CommonDb();
                }
            }
        }
        return sInstance;
    }
}
