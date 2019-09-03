package com.hudson.donglingmusic.UI.Item.Tab.HomeFirstTab;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.View.TabView.TabLayout.inner.BasePagerTabItem;

/**
 * Created by Hudson on 2019/1/5.
 */
public class HomeDefaultTab extends BasePagerTabItem {

    public HomeDefaultTab() {
        super(R.string.home_hot);
    }

    @Override
    protected View createContentView(Context context) {
        TextView textView = new TextView(context);
        textView.setText(R.string.home_hot);
        return textView;
    }
}
