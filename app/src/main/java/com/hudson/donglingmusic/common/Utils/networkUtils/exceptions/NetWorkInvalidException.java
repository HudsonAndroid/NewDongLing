package com.hudson.donglingmusic.common.Utils.networkUtils.exceptions;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.common.exception.AutoHandleException;
import com.hudson.donglingmusic.global.DongLingApplication;

/**
 * Created by Hudson on 2020/3/3.
 */
public class NetWorkInvalidException extends AutoHandleException {
    public NetWorkInvalidException() {
        super(DongLingApplication
                .getGlobalContext()
                .getResources()
                .getString(R.string.common_network_invalid));
    }
}
