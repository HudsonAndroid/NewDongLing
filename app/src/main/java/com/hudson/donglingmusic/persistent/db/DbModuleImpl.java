package com.hudson.donglingmusic.persistent.db;

import android.support.annotation.Keep;

import com.hudson.donglingmusic.persistent.db.impl.CommonDbImpl;
import com.hudson.donglingmusic.persistent.db.impl.UserDbImpl;
import com.hudson.donglingmusic.persistent.db.interfaces.ICommonDb;
import com.hudson.donglingmusic.persistent.db.interfaces.IUserDb;

/**
 * Created by Hudson on 2020/2/29.
 */
@Keep
public class DbModuleImpl implements DbModule {
    private CommonDbImpl mCommonDb = new CommonDbImpl();
    private UserDbImpl mUserDb = new UserDbImpl();

    @Override
    public ICommonDb getCommonDb() {
        return mCommonDb;
    }

    @Override
    public IUserDb getUserDb() {
        return mUserDb;
    }

    public void close(){
        mCommonDb.close();
        mUserDb.close();
    }

}
