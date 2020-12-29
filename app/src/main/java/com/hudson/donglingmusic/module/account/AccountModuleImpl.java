package com.hudson.donglingmusic.module.account;

import android.support.annotation.Keep;

import com.hudson.donglingmusic.entity.account.Account;
import com.hudson.donglingmusic.entity.account.DefaultAccount;

/**
 * Created by Hudson on 2020/3/27.
 */
@Keep
public class AccountModuleImpl implements AccountModule{
    private Account mAccount;

    public AccountModuleImpl() {
        mAccount = new DefaultAccount();
    }

    @Override
    public Account getAccount() {
        return mAccount;
    }
}
