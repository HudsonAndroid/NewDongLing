package com.hudson.donglingmusic.UI.View.TabView.TabLayout;

import android.content.Context;
import android.view.ViewGroup;

import com.hudson.donglingmusic.UI.Item.Tab.HomeFirstTab.HomeDefaultTab;
import com.hudson.donglingmusic.UI.Item.Tab.HomeFirstTab.HomeLocalMusicTab;
import com.hudson.donglingmusic.UI.View.TabView.TabLayout.inner.AbsTabPagerWrapperView;
import com.hudson.donglingmusic.UI.View.TabView.TabLayout.inner.BasePagerTabItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hudson on 2019/1/5.
 */
public class HomeFirstPage extends AbsTabPagerWrapperView {
    public HomeFirstPage(Context context, ViewGroup parent) {
        super(context, parent);
    }

    @Override
    protected List<BasePagerTabItem> getTabList() {
        List<BasePagerTabItem> contents = new ArrayList<>();
        contents.add(new HomeDefaultTab());
        contents.add(new HomeLocalMusicTab());
        return contents;
    }
}
