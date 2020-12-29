package com.hudson.donglingmusic.UI.Item.Tab.HomeFirstTab;

import android.content.Context;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.TabContentView.home.SongSheetPage;
import com.hudson.donglingmusic.UI.View.TabView.TabLayout.inner.tab.BasePagerTabItem;
import com.hudson.donglingmusic.UI.View.TabView.TabLayout.inner.tabContent.AbsTabContent;

/**
 * Created by Hudson on 2020/3/2.
 */
public class SongSheetTab extends BasePagerTabItem {
    public SongSheetTab() {
        super(R.string.home_song_sheet);
    }

    @Override
    protected AbsTabContent createContentView(Context context) {
        return new SongSheetPage(context);
    }
}
