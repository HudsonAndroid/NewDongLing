package com.hudson.donglingmusic.UI.View.TabView.RadioGroup.inner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.View.MyRadioGroup;
import com.hudson.donglingmusic.UI.View.TabView.ITabWrapperView;
import com.hudson.donglingmusic.UI.View.TabView.RadioGroup.inner.tab.BaseRadioTabItem;

import java.util.List;

/**
 * RadioGroup方式，这种方式下，适用于各个tab item
 * 毫无影响(相互独立)的情况
 * Created by Hudson on 2018/12/29.
 */
public abstract class AbsTabRadioWrapperView implements ITabWrapperView {
    protected Context mContext;
    protected BaseRadioTabItem mLastCheckedTab;
    protected final MyRadioGroup<BaseRadioTabItem> mContainer;
    protected final List<BaseRadioTabItem> mTabs;

    public AbsTabRadioWrapperView(Context context, ViewGroup parent){
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.custom_view_tabview_radio, parent, true);
        mContainer = (MyRadioGroup<BaseRadioTabItem>) parent.findViewById(R.id.rg_container);
        final LinearLayout mContentContainer = (LinearLayout) parent.findViewById(R.id.ll_container);
        mContainer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                BaseRadioTabItem checkedRadioButton = mContainer.getCheckedRadioButton();
                mContentContainer.removeAllViews();
                mContentContainer.addView(checkedRadioButton.getContentView(mContext));
                checkedRadioButton.loadAndRefreshView();//被选中，刷新
                if(mLastCheckedTab != null){
                    mLastCheckedTab.onUncheck();
                }
                checkedRadioButton.onCheck();
                mLastCheckedTab = checkedRadioButton;
            }
        });
        mTabs = getTabList(mContentContainer);
        for (int i = 0; i < mTabs.size(); i++) {
            BaseRadioTabItem item = mTabs.get(i);
            mContainer.addView(item);
            if(i == 0){
                item.setChecked(true);
            }
        }
    }

    /**
     * 获取tab列表
     * @param parent 仅用于测量的父布局
     * @return
     */
    protected abstract List<BaseRadioTabItem> getTabList(ViewGroup parent);

    @Override
    public void onDestroy(){
        if(mTabs != null){
            for (BaseRadioTabItem tab : mTabs) {
                tab.onDestroy();
            }
        }
    }
}
