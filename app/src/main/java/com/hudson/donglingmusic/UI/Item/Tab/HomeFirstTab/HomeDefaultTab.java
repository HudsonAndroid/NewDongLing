package com.hudson.donglingmusic.UI.Item.Tab.HomeFirstTab;

import android.content.Context;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.TabContentView.home.HomeHotPage;
import com.hudson.donglingmusic.UI.View.TabView.TabLayout.inner.tab.BasePagerTabItem;
import com.hudson.donglingmusic.UI.View.TabView.TabLayout.inner.tabContent.AbsTabContent;

/**
 * Created by Hudson on 2019/1/5.
 */
public class HomeDefaultTab extends BasePagerTabItem {

    public HomeDefaultTab() {
        super(R.string.home_hot);
    }

    @Override
    protected AbsTabContent createContentView(final Context context) {
        return new HomeHotPage(context);
    }


}
