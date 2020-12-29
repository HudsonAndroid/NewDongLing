package com.hudson.donglingmusic.persistent.db;

import android.support.annotation.Keep;

import com.hudson.donglingmusic.module.IModule;
import com.hudson.donglingmusic.module.ModuleTarget;
import com.hudson.donglingmusic.persistent.db.interfaces.ICommonDb;
import com.hudson.donglingmusic.persistent.db.interfaces.IUserDb;

/**
 * Created by Hudson on 2020/2/29.
 */
@ModuleTarget(DbModuleImpl.class)@Keep
public interface DbModule extends IModule {
    ICommonDb getCommonDb();
    IUserDb getUserDb();
}
