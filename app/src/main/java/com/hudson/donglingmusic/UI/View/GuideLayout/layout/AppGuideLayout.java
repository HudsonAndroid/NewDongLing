package com.hudson.donglingmusic.UI.View.GuideLayout.layout;

import android.content.Context;

import com.hudson.donglingmusic.UI.View.GuideLayout.step.IStep;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用引导ViewGroup
 * Created by hpz on 2019/3/1.
 */
public class AppGuideLayout extends GuideLayout {
    private int mCurrentStep;//当前的步骤，0表示尚未开始
    private final List<IStep> mSteps = new ArrayList<>();
    private GuideStepListener mListener;

    public AppGuideLayout(Context context) {
        super(context);
    }

    public AppGuideLayout addStep(IStep step){
        mSteps.add(step);
        return this;
    }

    /**
     * 开始
     * 注意：如果第一步存在需要准备的内容，请确保该内容准备完毕后操作
     */
    public void start(){
        show();
        mCurrentStep = 0;
        showNextStep();
    }

    public void setStepShowBeforeListener(GuideStepListener listener) {
        mListener = listener;
    }

    @Override
    public void showNextStep() {
        if(mCurrentStep < mSteps.size()){
            cleanFocusItem();
            removeAllViews();
            IStep step = mSteps.get(mCurrentStep);
            mCurrentStep ++;
            if(mListener != null){
                mListener.beforeShowStep(mCurrentStep);
            }
            step.showStep(this);
            prepareNextStep();//开始准备下一步
        }else{
            hide(false);
        }
    }

    /**
     * 开始下一步的准备工作
     */
    private void prepareNextStep() {
        if(mCurrentStep < mSteps.size()){
            mSteps.get(mCurrentStep).prepare();
        }
    }

    @Override
    public void hide(boolean isSkipInvoke) {
        super.hide(isSkipInvoke);
        if(mListener != null){
            mListener.onGuideComplete(isSkipInvoke);
        }
    }

    public interface GuideStepListener {
        /**
         * 显示步骤前
         * @param step 步骤的id，从1开始
         */
        void beforeShowStep(int step);

        /**
         * 当引导完成时回调
         * @param isSkipInvoke 如果是用户选择跳过触发的，该值为
         *                     true。规则由回调者触发（子步骤
         *                     中设置某个步骤的某个事件触发，
         *                     一般情况下是指第一步的跳过功能）
         */
        void onGuideComplete(boolean isSkipInvoke);
    }
}
