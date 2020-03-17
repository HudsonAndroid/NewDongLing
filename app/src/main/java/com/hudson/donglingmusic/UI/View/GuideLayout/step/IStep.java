package com.hudson.donglingmusic.UI.View.GuideLayout.step;

import com.hudson.donglingmusic.UI.View.GuideLayout.layout.GuideLayout;

/**
 * Created by hpz on 2019/3/1.
 */
public interface IStep {
    /**
     * 显示当前步骤
     * @param parent 步骤引导父布局
     */
    void showStep(GuideLayout parent);

    /**
     * 准备操作,例如初始化一些数据
     */
    void prepare();
}
