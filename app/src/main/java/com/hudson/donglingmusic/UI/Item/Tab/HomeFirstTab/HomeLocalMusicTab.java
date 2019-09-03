package com.hudson.donglingmusic.UI.Item.Tab.HomeFirstTab;

import android.content.Context;
import android.view.View;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.TabContentView.HomeLocalMusicPage;
import com.hudson.donglingmusic.UI.View.TabView.TabLayout.inner.BasePagerTabItem;

/**
 * Created by Hudson on 2019/1/5.
 */
public class HomeLocalMusicTab extends BasePagerTabItem{

    public HomeLocalMusicTab() {
        super(R.string.home_local_music);
    }

    @Override
    protected View createContentView(Context context) {
        return new HomeLocalMusicPage(context);
    }

}
