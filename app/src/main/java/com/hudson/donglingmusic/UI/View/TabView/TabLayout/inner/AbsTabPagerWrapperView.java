package com.hudson.donglingmusic.UI.View.TabView.TabLayout.inner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.View.TabView.ITabWrapperView;

import java.util.ArrayList;
import java.util.List;

/**
 * TabLayout + ViewPager组合方式
 * Created by hpz on 2018/12/29.
 */
public abstract class AbsTabPagerWrapperView implements ITabWrapperView {
    private Context mContext;

    public AbsTabPagerWrapperView(Context context, ViewGroup parent){
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.custom_view_tabview_pager, parent, true);
        TabLayoutWrapperView headerTab = (TabLayoutWrapperView) parent.findViewById(R.id.tl_tab_header);
        ViewPager viewPager = (ViewPager) parent.findViewById(R.id.vp_content);
        List<BasePagerTabItem> list = getTabList();
        for (BasePagerTabItem baseTabItem : list) {
            headerTab.addTabItem(baseTabItem);
        }
        viewPager.setAdapter(new MyPageAdapter(list));
        headerTab.setOnTabItemSelectedListener(new TabLayoutWrapperView.OnTabItemSelectedListener() {
            @Override
            public void onTabSelected(BasePagerTabItem tabItem) {
                tabItem.loadAndRefreshView();//选中该页才去加载数据
            }
        });
        headerTab.setupWithViewPager(viewPager);
    }

    protected abstract List<BasePagerTabItem> getTabList();

    private class MyPageAdapter extends PagerAdapter {
        private final List<BasePagerTabItem> mList = new ArrayList<>();

        MyPageAdapter(List<BasePagerTabItem> list){
            mList.clear();
            mList.addAll(list);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mList.get(position).getContentView(mContext);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position).getContentView(mContext));
        }
    }
}
