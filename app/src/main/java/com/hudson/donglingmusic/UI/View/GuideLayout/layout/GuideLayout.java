package com.hudson.donglingmusic.UI.View.GuideLayout.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.hudson.donglingmusic.UI.View.GuideLayout.focusEntity.FocusEntity;
import com.hudson.donglingmusic.common.Utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页面布局
 * 初始思路是通过获取activity根布局的ViewGroup结合
 * bringToFront方法把当前的布局置到整个布局树的最上层。
 * 在后续开发中发现如果遇到类似PopupWindow这类通过
 * WindowManager添加的布局，引导页无法显示在最上层，
 * 原因就是两者是由两个不同的windowManager管理的，因此
 * 引导页改为使用WindowManger实现，保证能显示在手机
 * 最上层。
 * Created by Hudson on 2019/2/25.
 */
public abstract class GuideLayout extends ConstraintLayout {
    private static final int COLOR_BG = 0xCC000000;//背景色,80%透明度
    private static final int COLOR_FOCUS = 0x00000000;//高亢区域覆盖的颜色层
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mParams;
    private final List<FocusEntity> mFocusList = new ArrayList<>();//高亢区域
    private PorterDuffXfermode mXfermode;
    private Paint mPaint;
    private float mNotifyHeight;

    //只有一个构造方法，不能通过布局文件方式定义
    public GuideLayout(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mParams = new WindowManager.LayoutParams();
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mNotifyHeight = CommonUtils.getStatusHeight();
    }

    /**
     * 显示引导页面
     */
    public void show(){
        mWindowManager.addView(this,mParams);
    }

    /**
     * 隐藏整个引导页
     */
    public void hide(boolean isSkipInvoke){
        setVisibility(GONE);
        mWindowManager.removeView(this);
    }

    public boolean isShowing(){
        return getVisibility() != GONE;
    }

    /**
     * 添加高亢区域
     * @param entity 高亢区域实例
     */
    public void addFocusItem(FocusEntity entity){
        mFocusList.add(entity);
        invalidate();
    }

    /**
     * 添加高亢区域
     * @param entities 高亢区域实例集
     */
    public void addFocusItems(List<FocusEntity> entities){
        mFocusList.addAll(entities);
        invalidate();
    }

    /**
     * 清空所有高亢区域
     */
    public void cleanFocusItem(){
        mFocusList.clear();
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        if(mFocusList.size() == 0){
            Log.w("GuideLayout","you don't have any focus range!");
            return ;
        }
        drawBackground(canvas);
        super.dispatchDraw(canvas);
    }

    private void drawBackground(Canvas canvas) {
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        mPaint.setColor(COLOR_BG);
        canvas.drawRect(0,0,getWidth(),getHeight(),mPaint);
        mPaint.setXfermode(mXfermode);
        mPaint.setColor(COLOR_FOCUS);
        canvas.translate(0,-mNotifyHeight);//activity的页面包含了个通知栏高度
        for (FocusEntity focusEntity : mFocusList) {
            focusEntity.drawFocus(canvas,mPaint);
        }
        canvas.restoreToCount(layerId);
    }

    public abstract void showNextStep();
}
