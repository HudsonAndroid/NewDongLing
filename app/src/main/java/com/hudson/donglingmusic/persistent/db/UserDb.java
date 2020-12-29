package com.hudson.donglingmusic.persistent.db;

import com.hudson.donglingmusic.controller.ModuleManager;

/**
 * Created by Hudson on 2020/3/20.
 */
public class UserDb extends BaseDb {
    private static final String USER_DB_PREFIX = "userDb_";
    private String mUid;
    private static UserDb sInstance;

    public static UserDb getInstance() {
        String uid = ModuleManager.getAccountModule().getAccount().getUserId();
        if (sInstance == null) {
            //未生成过单例
            sInstance = new UserDb(uid);
        }else {
            if (!uid.equals(sInstance.mUid)){
                //帐号发生变化
                sInstance.close();
                sInstance = new UserDb(uid);
            }
        }
        return sInstance;
    }

    private UserDb(String uid) {
        super(USER_DB_PREFIX + uid + ".db");
        mUid = uid;
    }
}
