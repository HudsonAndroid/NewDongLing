package com.hudson.donglingmusic.common.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.hudson.donglingmusic.global.DongLingApplication;


public class CommonUtils {

	public static Context getContext() {
		return DongLingApplication.getGlobalContext();
	}

	/**
	 * 获取一个资源文件中的字符串
	 * 
	 * @param id
	 * @return
	 */
	public static String getString(int id) {
		return getContext().getResources().getString(id);
	}

	/**
	 * 获取一个资源文件中的字符串数组
	 * 
	 * @param id
	 * @return
	 */
	public static String[] getStringArray(int id) {
		return getContext().getResources().getStringArray(id);
	}

	/**
	 * 获取一个资源文件中的图片
	 * 
	 * @param id
	 * @return
	 */
	public static Drawable getDrawable(int id) {
		return getContext().getResources().getDrawable(id);
	}

	/**
	 * 获取一个资源文件中的颜色
	 * 
	 * @param id
	 * @return
	 */
	public static int getColor(int id) {
		return getContext().getResources().getColor(id);
	}

	/**
	 * 获取一个资源文件中的尺寸值
	 * 
	 * @param id
	 * @return 像素值
	 */
	public static float getDimension(int id) {
		return getContext().getResources().getDimensionPixelSize(id);
	}

	public static int getDefaultColor(String resName){
		Context context = getContext();
		Resources resources = context.getResources();
		return resources.getColor(resources
				.getIdentifier(resName, "color", context.getPackageName()));
	}

	/**
	 * 将dp值转成px值
	 * @param dp
	 * @return 像素 int
	 */
	public static int dp2px(int dp) {
		return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dp, getContext().getResources().getDisplayMetrics()) + 0.5f);
	}
	/**
	 * 将dp转成px
	 * @param dp
	 * @return 像素 float
	 */
	public static float dp2px(float dp) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getContext().getResources().getDisplayMetrics());
	}
	/**
	 * 将sp转成px
	 * @param sp
	 * @return 像素 int 
	 */
	public static int sp2px(int sp) {
		return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
				getContext().getResources().getDisplayMetrics()) + 0.5f);
	}
	/**
	 * 将sp转成px
	 * @param sp
	 * @return 像素float
	 */
	public static float sp2px(float sp) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
				getContext().getResources().getDisplayMetrics());
	}

	public static boolean requestPermission(Activity activity,String permission,int requestCode){
		if (ContextCompat.checkSelfPermission(activity,permission)
				!= PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(activity,new String[]{permission},requestCode);
		}else{
			return true;
		}
		return false;
	}

	/**
	 * Context theme wrapper to activity.
	 *
	 * @param context the context
	 * @return the activity
	 */
	public static Activity contextThemeWrapperToActivity(@NonNull Context context) {
		if (context instanceof Activity)
			return (Activity) context;
		else if (context instanceof ContextWrapper)
			return contextThemeWrapperToActivity(((ContextWrapper) context).getBaseContext());
		throw new IllegalArgumentException();
	}

	/**
	 * 获取通知栏高度
	 * @return
	 */
	public static float getStatusHeight(){
		Resources system = Resources.getSystem();
		return system.getDimension(system.getIdentifier("status_bar_height","dimen","android"));
	}

	/**
	 * 获取屏幕高度
	 * @return
	 */
	public static int getScreenHeight(){
		DisplayMetrics metrics = DongLingApplication.getGlobalContext()
				.getResources().getDisplayMetrics();
		return metrics.heightPixels;
	}

	public static boolean isActivityDestroy(Context context){
		try{
			Activity activity = contextThemeWrapperToActivity(context);
			return isDestroy(activity);
		}catch (IllegalArgumentException e){
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 判断Activity是否Destroy
	 * @param activity
	 * @return
	 */
	public static boolean isDestroy(Activity activity) {
		if (activity== null || activity.isFinishing() && activity.isDestroyed()) {
			return true;
		}else{
			return false;
		}
	}
}
