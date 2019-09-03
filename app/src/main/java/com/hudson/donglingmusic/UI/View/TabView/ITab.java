package com.hudson.donglingmusic.UI.View.TabView;

import android.content.Context;
import android.view.View;

/**
 * Created by hpz on 2018/11/5.
 */
public interface ITab {
    /**
     * 获取对应该tab的内容布局
     * @return
     */
    View getContentView(Context context);

    /**
     * 刷新,可能是加载网络数据
     */
    void loadAndRefreshView();
}
