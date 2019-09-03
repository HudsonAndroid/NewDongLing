package com.hudson.donglingmusic.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
	
}
