package com.hudson.donglingmusic.UI.View.TabView.RadioGroup.inner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.View.MyRadioGroup;
import com.hudson.donglingmusic.UI.View.TabView.ITabWrapperView;

import java.util.List;

/**
 * RadioGroup方式，这种方式下，适用于各个tab item
 * 毫无影响(相互独立)的情况
 * Created by hpz on 2018/12/29.
 */
public abstract class AbsTabRadioWrapperView implements ITabWrapperView {
    protected Context mContext;

    public AbsTabRadioWrapperView(Context context, ViewGroup parent){
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.custom_view_tabview_radio, parent, true);
        final MyRadioGroup<BaseRadioTabItem> container = (MyRadioGroup<BaseRadioTabItem>) parent.findViewById(R.id.rg_container);
        final LinearLayout mContentContainer = (LinearLayout) parent.findViewById(R.id.ll_container);
        container.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                BaseRadioTabItem checkedRadioButton = container.getCheckedRadioButton();
                mContentContainer.removeAllViews();
                mContentContainer.addView(checkedRadioButton.getContentView(mContext));
                checkedRadioButton.loadAndRefreshView();//被选中，刷新
                container.setBackgroundResource(checkedRadioButton.getTabViewHeaderBgId());
            }
        });
        List<BaseRadioTabItem> items = getTabList();
        for (int i = 0; i < items.size(); i++) {
            BaseRadioTabItem item = items.get(i);
            container.addView(item);
            if(i == 0){
                item.setChecked(true);
            }
        }
    }

    protected abstract List<BaseRadioTabItem> getTabList();
}
