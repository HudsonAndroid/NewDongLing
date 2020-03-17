package com.hudson.donglingmusic.UI.View.GuideLayout.shape;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by hpz on 2019/2/25.
 */
@TargetApi(21)
public class RoundRectShape implements IShape {
    private static final int RADIUS_DIMENSION = 5;//px
    private int mRadius = RADIUS_DIMENSION;

    @Override
    public void drawShape(Canvas canvas, int width, int height, Paint paint) {
        canvas.drawRoundRect(0,0,width, height,mRadius,mRadius,paint);
    }

    public void setRadius(int radius) {
        mRadius = radius;
    }
}
