package com.hudson.donglingmusic.UI.View.TabView.TabLayout.inner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.hudson.donglingmusic.UI.View.TabView.TabLayout.inner.tab.BasePagerTabItem;

/**
 * TabLayout的封装，基于BaseTabItem
 * Created by Hudson on 2018/11/5.
 */
public class TabLayoutWrapperView extends TabLayout {
    private OnTabItemSelectedListener mOnTabItemSelectedListener;

    public TabLayoutWrapperView(Context context) {
        this(context, null);
    }

    public TabLayoutWrapperView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabLayoutWrapperView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTabMode(TabLayout.MODE_FIXED);
        setTabGravity(TabLayout.GRAVITY_FILL);
    }

    /**
     * 添加标签tab，不使用addTab方法
     * @param baseTabItem 标签
     */
    public void addTabItem(BasePagerTabItem baseTabItem){
        Tab tab = newTab();
        tab.setTag(baseTabItem);
        baseTabItem.setTab(tab);
        addTab(tab);
    }

    public void setOnTabItemSelectedListener(OnTabItemSelectedListener onTabItemSelectedListener) {
        mOnTabItemSelectedListener = onTabItemSelectedListener;
    }

    public interface OnTabItemSelectedListener{
        void onTabSelected(BasePagerTabItem tabItem);
    }

    //原始TabLayout的setupWithViewPager逻辑是先移除已添加的tab，然后重新添加，
    //但是我们这里对tab有一层的封装，不能够使用原始逻辑，否则会出现问题
    @Override
    public void setupWithViewPager(@NonNull ViewPager viewPager) {
        final PagerAdapter adapter = viewPager.getAdapter();
        if (adapter == null) {
            throw new IllegalArgumentException("ViewPager does not have a PagerAdapter set");
        }
        // Now we'll add our page change listener to the ViewPager
        viewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(this));
        // Now we'll add a tab selected listener to set ViewPager's current item，此处给TabLayout设置了
        //一个监听器（用于切换ViewPager），如果外界回调setupWithViewPager比设置监听器晚，那么外界的监听器是
        //无效的【同时，外界也不应该自行设置监听器，否则ViewPager将会无法正常与TabLayout绑定】
        setOnTabSelectedListener(new ViewPagerOnTabSelectedListener(viewPager){
            @Override
            public void onTabSelected(Tab tab) {
                super.onTabSelected(tab);
                if(mOnTabItemSelectedListener != null){
                    BasePagerTabItem tag = (BasePagerTabItem) tab.getTag();
                    if(tag != null){
                        mOnTabItemSelectedListener.onTabSelected(tag);
                    }
                }
            }
        });
        // Make sure we reflect the currently set ViewPager item,选中第一个页面
        Tab tab = getTabAt(0);
        if(tab != null){
            viewPager.setCurrentItem(0);
            tab.select();
            //首次选中并不会触发onTabSelected，触发的是onTabReselected方法，原因暂时未知
            if(mOnTabItemSelectedListener != null){
                BasePagerTabItem tag = (BasePagerTabItem) tab.getTag();
                if(tag != null){
                    mOnTabItemSelectedListener.onTabSelected(tag);
                }
            }
        }
    }
}
