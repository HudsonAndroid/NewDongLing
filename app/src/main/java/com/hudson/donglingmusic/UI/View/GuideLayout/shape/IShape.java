package com.hudson.donglingmusic.UI.View.GuideLayout.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 图形Shape
 * Created by hpz on 2019/2/25.
 */
public interface IShape {
    /**
     * 绘制图形
     * @param canvas 画布
     * @param width 绘制的宽度
     * @param height 绘制的高度
     * @param paint 画笔
     */
    void drawShape(Canvas canvas, int width, int height, Paint paint);
}
