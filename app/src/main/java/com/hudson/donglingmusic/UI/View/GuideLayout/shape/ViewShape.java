package com.hudson.donglingmusic.UI.View.GuideLayout.shape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;

import com.hudson.donglingmusic.common.Utils.asyncUtils.AsyncTask;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoInBackground;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoOnFail;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoOnSuccess;
import com.hudson.donglingmusic.common.Utils.bitmapUtils.BitmapUtils;

/**
 * 按照View自身的轮廓高亢显示view，可以显示不规则图形效果
 * Created by Hudson on 2019/3/4.
 */
public class ViewShape implements IShape{
    private Bitmap mViewBitmap;
    private IShape mSpareShape;//备用shape，由于获取view的图片是异步的，如果绘制时没有加载完成使用备用方案
    private View mView;

    public ViewShape(@NonNull final View view) {
        mView = view;
    }

    public void prepare() {
        AsyncTask.newTask(Bitmap.class).useDbThreadPool().doInBackground(new IDoInBackground<Bitmap>() {
            @Override
            public Bitmap run() {
                //对产生的图片进行处理。这一步不能忽视，原因是通过view直接产生的图片
                //占用内存较大，如果不进行压缩处理，后台会被系统立即回收掉，导致无法
                //按照view轮廓显示
                return BitmapUtils.getViewBitmap(mView);
            }
        }).doOnSuccess(new IDoOnSuccess<Bitmap>() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                mViewBitmap = bitmap;
            }
        }).doOnFail(new IDoOnFail() {
            @Override
            public void onFail(Throwable e) {
                e.printStackTrace();
                mViewBitmap = null;
            }
        }).execute();
    }

    private void initSpareShape(){
        if(Build.VERSION.SDK_INT >= 21){
            mSpareShape = new RoundRectShape();
        }else{
            mSpareShape = new RectShape();
        }
    }

    @Override
    public void drawShape(Canvas canvas, int width, int height, Paint paint) {
        if(mViewBitmap != null && !mViewBitmap.isRecycled()){
            //此处不要传入paint参数，以确保不使用xfermode模式
            canvas.drawBitmap(mViewBitmap,0,0,null);
        }else{
            initSpareShape();
            mSpareShape.drawShape(canvas,width,height,paint);
        }
    }
}
