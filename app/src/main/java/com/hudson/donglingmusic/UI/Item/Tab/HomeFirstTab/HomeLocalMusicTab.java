package com.hudson.donglingmusic.UI.Item.Tab.HomeFirstTab;

import android.content.Context;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.TabContentView.home.HomeLocalMusicPage;
import com.hudson.donglingmusic.UI.View.TabView.TabLayout.inner.tab.BasePagerTabItem;
import com.hudson.donglingmusic.UI.View.TabView.TabLayout.inner.tabContent.AbsTabContent;

/**
 * Created by Hudson on 2019/1/5.
 */
public class HomeLocalMusicTab extends BasePagerTabItem{

    public HomeLocalMusicTab() {
        super(R.string.home_local_music);
    }

    @Override
    protected AbsTabContent createContentView(Context context) {
        return new HomeLocalMusicPage(context);
    }

}
