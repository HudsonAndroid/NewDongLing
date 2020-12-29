package com.hudson.donglingmusic.UI.Item.TabContentView.home;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.View.TabView.TabLayout.inner.tabContent.BaseAsyncTabContent;
import com.hudson.donglingmusic.UI.adapter.HomeMineAdapter;
import com.hudson.donglingmusic.controller.ModuleManager;
import com.hudson.donglingmusic.entity.HomeMineData;
import com.hudson.donglingmusic.entity.SheetDetail;

import java.util.List;

/**
 * Created by Hudson on 2020/3/21.
 */
public class HomeMinePage extends BaseAsyncTabContent<HomeMineData> {
    private RecyclerView mContent;
    private TextView mUserName;
    private HomeMineAdapter mAdapter;

    public HomeMinePage(Context context) {
        super(context);
    }

    @Override
    protected void initContent(ConstraintLayout parent) {
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page_home_mine,parent);
        mContent = (RecyclerView) parent.findViewById(R.id.rv_content);
        mUserName = (TextView) parent.findViewById(R.id.tv_user_name);
        LinearLayoutManager layoutManager = new LinearLayoutManager(parent.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mContent.setLayoutManager(layoutManager);
        mAdapter = new HomeMineAdapter();
        mContent.setAdapter(mAdapter);
    }

    @Override
    protected HomeMineData getDataBackground() {
        return HomeMineData.fetch();
    }

    @Override
    protected void doOnSuccess(HomeMineData data) {
        mUserName.setText(ModuleManager.getAccountModule().getAccount().getUserName());
        List<SheetDetail> otherSheets = data.getOtherSheets();
        Log.e("hudson","其他歌单大小"+otherSheets.size());
        mAdapter.setSheets(otherSheets);
    }
}
