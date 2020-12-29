package com.hudson.donglingmusic.module.skin.exception;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.global.DongLingApplication;

/**
 * Created by Hudson on 2020/3/25.
 */
public class SkinEmptyException extends RuntimeException {

    public SkinEmptyException() {
        super(DongLingApplication.getGlobalContext().getString(R.string.common_skin_path_is_empty));
    }
}
