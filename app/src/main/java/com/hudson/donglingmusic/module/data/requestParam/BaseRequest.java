package com.hudson.donglingmusic.module.data.requestParam;

import com.hudson.donglingmusic.global.ConstVar;

/**
 * Created by Hudson on 2020/2/29.
 */
public abstract class BaseRequest implements IRequestParam {

    @Override
    public String getUrl() {
        return ConstVar.BASE +"&method="+ getMethod() + getExtendParam();
    }

    protected abstract String getMethod();

    protected abstract String getExtendParam();

    @Override
    public int getType() {
        return TYPE_GET;
    }

}
