package com.hudson.donglingmusic.common.Utils.PermissionUtils;

import android.app.Activity;

/**
 * Created by Hudson on 2020/2/26.
 */
public interface PermissionCallback {

    void onSuccess(Activity activity);

    void onFailed(Activity activity);
}
