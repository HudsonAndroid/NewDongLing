package com.hudson.donglingmusic.UI.Item.Tab.HomeFirstTab;

import android.content.Context;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.TabContentView.home.HomeMinePage;
import com.hudson.donglingmusic.UI.View.TabView.TabLayout.inner.tab.BasePagerTabItem;
import com.hudson.donglingmusic.UI.View.TabView.TabLayout.inner.tabContent.AbsTabContent;

/**
 * Created by Hudson on 2020/3/21.
 */
public class HomeMineTab extends BasePagerTabItem {
    public HomeMineTab() {
        super(R.string.home_mine);
    }

    @Override
    protected AbsTabContent createContentView(Context context) {
        return new HomeMinePage(context);
    }
}
