package com.hudson.donglingmusic.entity;

import com.hudson.donglingmusic.common.Utils.jsonUtils.annotions.JsonKey;

/**
 * Created by Hudson on 2020/3/2.
 */
public class BaseResult {
    public static final int SUCCESS_CODE = 22000;

    @JsonKey("error_code")
    private int mErrorCode;

    public boolean isSuccess(){
        return mErrorCode == SUCCESS_CODE;
    }
}
