package com.hudson.donglingmusic.UI.View.TabView.TabLayout.inner.tabContent;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by Hudson on 2020/2/29.
 */
public abstract class AbsTabContent extends RelativeLayout {

    public AbsTabContent(Context context) {
        super(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(layoutParams);
        initContentView(this);
    }

    protected abstract void initContentView(ViewGroup parent);

    public void onDestroy(){}

    public void onCheck(){}

    public void onUncheck(){}
}
