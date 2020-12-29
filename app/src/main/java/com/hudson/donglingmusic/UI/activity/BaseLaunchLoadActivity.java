package com.hudson.donglingmusic.UI.activity;

/**
 * Created by Hudson on 2020/3/3.
 */
public abstract class BaseLaunchLoadActivity<T> extends BaseFetchActivity<T> {

    @Override
    protected void initData() {
        super.initData();
        fetchData();
    }
}
