package com.hudson.donglingmusic.common.Utils.PermissionUtils;

import android.app.Activity;
import android.os.Build;
import android.support.v4.content.PermissionChecker;

import com.tbruyelle.rxpermissions.RxPermissions;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Hudson on 2020/2/26.
 */
public class PermissionUtils {

    public static void request(final Activity activity, final PermissionCallback callback, final String... permissions){
        if(callback==null) {
            throw new IllegalArgumentException("the permission callback is null");
        }
        //调用RxPermission 动态申请权限,23版本以下使用一般判断
        getObservableBySdk(activity,permissions).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean granted) {
                if(granted) {
                    /*权限授予成功回调,但是目前发现小米有些手机的位置权限并没遵循Google标准，
                    拒绝后仍然会返回TRUE，致使回调成功。因此需要进行二次判断。
                     */
                    if(isContainExceptionBrand()) {
                        if(!isGrant(activity,permissions)) {
                            granted = false;
                        }
                    }
                }
                if(granted){
                    callback.onSuccess(activity);
                }else{
                    callback.onFailed(activity);
                }
            }
        });
    }

    public static boolean isContainExceptionBrand() {
        boolean isExceptionBrand =false;
        if(Build.BRAND.contains("Xiaomi")) {
            isExceptionBrand = true;
        }
        return isExceptionBrand;
    }

    private static Observable<Boolean> getObservableBySdk(final Activity activity, final String... permissions) {
        Observable<Boolean> ob;
        if(isSdkOver23()){
            ob = RxPermissions.getInstance(activity).request(permissions);
        } else {
            ob = Observable.just(isGrant(activity,permissions));
        }
        return ob;
    }
    /**
     * 判断系统版本是否是6.0及以上
     */
    private static boolean isSdkOver23(){
        return Build.VERSION.SDK_INT>=23;
    }
    /**
     * 判断sdk23以下的权限情况
     */
    public static boolean isGrant(Activity activity,String... permission) {
        boolean grantResult = true;
        for(String p : permission) {
            grantResult&=(PermissionChecker.checkSelfPermission(activity,p)== PermissionChecker.PERMISSION_GRANTED);
        }
        return grantResult;
    }
}
