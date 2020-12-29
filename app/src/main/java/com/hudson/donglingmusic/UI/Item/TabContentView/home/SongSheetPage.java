package com.hudson.donglingmusic.UI.Item.TabContentView.home;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.ViewHolder.LoadMoreViewHolder;
import com.hudson.donglingmusic.UI.View.TabView.TabLayout.inner.tabContent.BaseAsyncTabContent;
import com.hudson.donglingmusic.UI.activity.SongSheetDetailActivity;
import com.hudson.donglingmusic.UI.adapter.SongSheetPageAdapter;
import com.hudson.donglingmusic.entity.SongSheet;

import java.util.List;

/**
 * Created by Hudson on 2020/3/2.
 */
public class SongSheetPage extends BaseAsyncTabContent<SongSheet> {
    private static final int LOAD_PAGE_SIZE = 10;
    private int mPageNo = 0;
    private RecyclerView mContainer;
    private SongSheetPageAdapter mAdapter;
    private RecyclerView.OnScrollListener mScrollListener;
    private GridLayoutManager mManager;

    @Override
    protected void initContent(ConstraintLayout parent) {
        Context context = parent.getContext();
        LayoutInflater.from(context).inflate(R.layout.item_page_song_sheet,parent);
        mContainer = (RecyclerView) parent.findViewById(R.id.rv_container);
        mManager = new GridLayoutManager(context,2);
        mManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){

            @Override
            public int getSpanSize(int position) {
                RecyclerView.Adapter adapter = mContainer.getAdapter();
                if(adapter != null){
                    int type = adapter.getItemViewType(position);
                    if(type == SongSheetPageAdapter.TYPE_NORMAL){//实际内容的情况下是2个
                        return 1;
                    }else{//其他,一个item占据一行
                        return 2;
                    }
                }
                return 0;
            }
        });
        mContainer.setLayoutManager(mManager);
        mAdapter = new SongSheetPageAdapter();
        mContainer.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new SongSheetPageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, String listId,String imgUrl) {
                //打开网络歌单页面
                SongSheetDetailActivity.startWithTransition((Activity)getContext(),v,listId,imgUrl);
            }
        });
    }

    public SongSheetPage(Context context) {
        super(context);
    }

    private void loadMore(){
        mLoadTask = null;
        mPageNo += 1;
        loadData();
    }

    @Override
    protected void loadDataBefore() {
        super.loadDataBefore();
        mAdapter.notifyLoadStatus(LoadMoreViewHolder.STATUS_LOADING);
    }

    @Override
    protected SongSheet getDataBackground() {
        return SongSheet.fetchData(mPageNo,LOAD_PAGE_SIZE,mForceServer);
    }

    @Override
    protected void doOnSuccess(SongSheet data) {
        List<SongSheet.SheetItem> songSheets = data.getSongSheets();
        if(songSheets != null){
            mAdapter.updateSheet(songSheets,data.hasMore());
        }
    }

    @Override
    protected void doOnFailed(Throwable e) {
        super.doOnFailed(e);
        mAdapter.notifyLoadStatus(LoadMoreViewHolder.STATUS_LOAD_FAILED);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    //注意：getChildCount是获取屏幕上可以看到的控件个数，getItemCount获取的是所有个数
                    if(mAdapter.canLoadMore()
                            &&mManager.findLastVisibleItemPosition() == mManager.getItemCount()-1){
                        //已经滑到底部了
                        loadMore();
                    }
                }
            }
        };
        mContainer.addOnScrollListener(mScrollListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mContainer.removeOnScrollListener(mScrollListener);
    }

    @Override
    protected boolean forbidTabLoadView() {
        return true;
    }
}
