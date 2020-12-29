package com.hudson.donglingmusic.UI.View.TabView.RadioGroup.inner;

import android.content.Context;
import android.view.ViewGroup;

import com.hudson.donglingmusic.UI.View.TabView.IRefreshView;
import com.hudson.donglingmusic.UI.View.TabView.RadioGroup.inner.tab.BaseRadioTabItem;

/**
 * 可刷新的TabView(可以重新加载的TabView，原有已经能够在切换到指定Tab开始加载)
 * Created by Hudson on 2019/5/5.
 */
public abstract class AbsRefreshableTabView extends AbsTabRadioWrapperView {

    public AbsRefreshableTabView(Context context, ViewGroup parent) {
        super(context, parent);
    }

    /**
     * 刷新tab
     */
    public void refreshTab(){
        if(mTabs != null){
            for (BaseRadioTabItem tabItem : mTabs) {
                if(tabItem instanceof IRefreshView){
                    ((IRefreshView) tabItem).refresh();
                }
            }
        }
    }
}
