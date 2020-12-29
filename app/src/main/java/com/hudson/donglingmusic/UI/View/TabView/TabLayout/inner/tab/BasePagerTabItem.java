package com.hudson.donglingmusic.UI.View.TabView.TabLayout.inner.tab;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.View;

import com.hudson.donglingmusic.UI.View.TabView.ILoadDataView;
import com.hudson.donglingmusic.UI.View.TabView.ITab;
import com.hudson.donglingmusic.UI.View.TabView.TabLayout.inner.tabContent.AbsTabContent;


/**
 * TabLayout类型的Tab
 * Created by Hudson on 2018/11/5.
 */
public abstract class BasePagerTabItem implements ITab {
    private String mDesc;
    private int mDescId;
    private AbsTabContent mContentView;
    private boolean mNeedLoadData = false;//标识该tab是否需要在初始化了内容布局之后立刻开始加载数据

    public BasePagerTabItem(String desc){
        mDesc = desc;
    }

    public BasePagerTabItem(int descId){
        mDescId = descId;
    }

    /**
     * 给TabLayout中的tab item设置本类暂存的一些信息(这里是标题).
     * 由于TabLayout.Tab类是final的，且构造方法包可见
     * 因此无法直接继承，添加时必须在TabLayout
     * 或者通过TabLayout的对象添加。
     * @param tab
     */
    public void setTab(TabLayout.Tab tab){
        if(mDescId != 0){
            tab.setText(mDescId);
        }
        if(!TextUtils.isEmpty(mDesc)){
            tab.setText(mDesc);
        }
    }

    @Override
    public void loadAndRefreshView(){
        if(mContentView == null){//当前内容布局尚未初始化，需要在初始化之后立刻加载数据
            mNeedLoadData = true;
        }else if(mContentView instanceof ILoadDataView){
            ((ILoadDataView) mContentView).loadData();
        }
    }

    @Override
    public View getContentView(Context context) {
        if(mContentView == null){
            mContentView = createContentView(context);
            if(mNeedLoadData){
                mNeedLoadData = false;
                loadAndRefreshView();
            }
        }
        return mContentView;
    }

    protected abstract AbsTabContent createContentView(Context context);

    @Override
    public void onDestroy(){
        if(mContentView != null){
            mContentView.onDestroy();
        }
    }

    @Override
    public void onCheck(){
        if(mContentView != null){
            mContentView.onCheck();
        }
    }

    @Override
    public void onUncheck(){
        if(mContentView != null){
            mContentView.onUncheck();
        }
    }
}
