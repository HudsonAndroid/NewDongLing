package com.hudson.donglingmusic.UI.View.GuideLayout.focusEntity;

import android.view.View;

import com.hudson.donglingmusic.UI.View.GuideLayout.shape.ViewShape;


/**
 * 按照view的原始轮廓显示高亢区域
 * 这里的轮廓是指去除了透明部分的轮廓
 * Created by hpz on 2019/3/4.
 */
public class FocusViewEntity extends FocusEntity {
    private final ViewShape mShape;

    /**
     * 展示view的轮廓
     * 注意：添加的view需要确保已经布局完成，否则实际效果可能是缺少内容的
     * @param view 需要高亢的view
     */
    public FocusViewEntity(View view) {
        super(view);
        mShape = new ViewShape(view);
        setShape(mShape);
    }

    @Override
    public void prepare() {
        super.prepare();
        mShape.prepare();
    }
}
