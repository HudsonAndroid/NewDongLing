package com.hudson.donglingmusic.UI.View.TabView.RadioGroup.inner.tabContent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Tab内容
 * 适用于RadioGroup类型
 * Created by Hudson on 2019/3/12.
 */
public abstract class AbsRadioTabContent {
    protected View mRootView;

    public AbsRadioTabContent(ViewGroup parent){
        //注意：必须限定attachToRoot为false,否则将会不符合实际需求
        mRootView = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(),parent,false);
    }

    protected abstract int getLayoutId();

    public View getContent() {
        return mRootView;
    }

    public void onDestroy(){}

    public void onCheck(){}

    public void onUncheck(){}
}
