package com.hudson.donglingmusic.entity.account;

/**
 * Created by Hudson on 2020/3/27.
 */
public class DefaultAccount extends Account {
    private final static String USER_NAME = "hudson";
    private final static String USER_ID = "6666666";

    public DefaultAccount() {
        setUserId(USER_ID);
        setUserName(USER_NAME);
    }
}
