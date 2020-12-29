package com.hudson.donglingmusic.module.data.requestParam;

/**
 * Created by Hudson on 2020/2/29.
 */
public class HomePicRequest extends BaseRequest {
    private int mCount;

    public HomePicRequest(int picCount){
        mCount = picCount;
    }

    @Override
    protected String getMethod() {
        return "baidu.ting.plaza.getFocusPic";
    }

    @Override
    protected String getExtendParam() {
        return "&num="+mCount;
    }
}
