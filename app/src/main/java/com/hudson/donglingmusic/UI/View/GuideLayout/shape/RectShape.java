package com.hudson.donglingmusic.UI.View.GuideLayout.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 矩形高亢区域
 * Created by hpz on 2019/2/25.
 */
public class RectShape implements IShape {
    @Override
    public void drawShape(Canvas canvas, int width, int height, Paint paint) {
        canvas.drawRect(0,0,width,height,paint);
    }
}
