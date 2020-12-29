package com.hudson.donglingmusic.module.account;

import com.hudson.donglingmusic.entity.account.Account;
import com.hudson.donglingmusic.module.IModule;
import com.hudson.donglingmusic.module.ModuleTarget;

/**
 * Created by Hudson on 2020/3/27.
 */
@ModuleTarget(AccountModuleImpl.class)
public interface AccountModule extends IModule {
    Account getAccount();
}
