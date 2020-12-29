package com.hudson.donglingmusic.UI.View.GuideLayout.focusEntity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;

import com.hudson.donglingmusic.UI.View.GuideLayout.shape.IShape;
import com.hudson.donglingmusic.UI.View.GuideLayout.shape.RectShape;
import com.hudson.donglingmusic.UI.View.GuideLayout.shape.RoundRectShape;


/**
 * 高亢显示部分实例
 * Created by Hudson on 2019/2/25.
 */
public class FocusEntity {
    private View mView;//用于承载区域的控件,由于控件本身绘制可能未完成，需要使用view作为成员
    private Rect mRect;//针对自定义某个区域高亢的内容，未必是某个控件区域
    private IShape mShape;
    private int[] mLocation = new int[2];

    public FocusEntity(View view) {
        mView = view;
        initShape();
    }

    public FocusEntity(Rect rect) {
        mRect = rect;
        initShape();
    }

    private void initShape(){
        if(Build.VERSION.SDK_INT >= 21){
            mShape = new RoundRectShape();
        }else{
            mShape = new RectShape();
        }
    }

    public void setShape(IShape shape) {
        mShape = shape;
    }

    public void drawFocus(Canvas canvas, Paint paint){
        if(mLocation[0] <= 0 || mLocation[1] <= 0){
            initLocation();
        }
        int width,height;
        if(mRect != null){
            width = mRect.width();
            height = mRect.height();
        }else if(mView != null){
            width = mView.getWidth();
            height = mView.getHeight();
        }else{
            return ;
        }
        canvas.save();
        canvas.translate(mLocation[0],mLocation[1]);
        mShape.drawShape(canvas,width,height,paint);
        canvas.restore();
    }

    private void initLocation(){
        if(mLocation[0] <= 0 || mLocation[1] <= 0){
            if(mRect != null){
                mLocation[0] = mRect.left;
                mLocation[1] = mRect.top;
            }else if(mView != null){
                mView.getLocationOnScreen(mLocation);
            }
        }
    }

    public int getHeight(){
        if(mRect != null){
            return mRect.height();
        }else if(mView != null){
            return mView.getHeight();
        }else{
            return 0;
        }
    }

    public void prepare(){
        //maybe do nothing
        initLocation();
    }
}
