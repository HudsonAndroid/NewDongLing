package com.hudson.donglingmusic.UI.Item.TabContentView.home;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.OnItemClickListener;
import com.hudson.donglingmusic.UI.View.TabView.TabLayout.inner.tabContent.BaseAsyncTabContent;
import com.hudson.donglingmusic.UI.adapter.CommonMusicAdapter;
import com.hudson.donglingmusic.common.Utils.MusicUtils.LocalMusicUtils;
import com.hudson.donglingmusic.entity.MusicEntity;
import com.hudson.donglingmusic.global.DongLingApplication;
import com.hudson.donglingmusic.service.IPlayerController;

import java.util.List;

/**
 * Created by Hudson on 2019/1/5.
 */
public class HomeLocalMusicPage extends BaseAsyncTabContent<List<MusicEntity>> implements OnItemClickListener<MusicEntity> {
//    private LocalMusicAdapter mAdapter;
    private CommonMusicAdapter mAdapter;
    private static final String LOCAL_UNIQUE_PLAY_LIST = "local_play_list";

    public HomeLocalMusicPage(Context context) {
        super(context);
    }

    @Override
    protected void initContent(ConstraintLayout parent) {
        Context context = parent.getContext();
        LayoutInflater.from(context).inflate(R.layout.page_home_local_music, parent);
        RecyclerView recyclerView = findViewById(R.id.rv_list);
        LinearLayoutManager layout = new LinearLayoutManager(context);
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layout);
        mAdapter = new CommonMusicAdapter();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(MusicEntity item, int position) {
        IPlayerController controller = DongLingApplication.getPlayerController();
        if(controller != null){
            controller.setPlayList(mAdapter.getDatas(),LOCAL_UNIQUE_PLAY_LIST);
            controller.play(position);
        }
    }

    @Override
    protected List<MusicEntity> getDataBackground() {
        return LocalMusicUtils.getLocalMusics(getContext());
    }

    @Override
    protected void doOnSuccess(List<MusicEntity> musicEntities) {
        if(musicEntities != null && musicEntities.size() > 0){
            IPlayerController controller = DongLingApplication.getPlayerController();
            if(controller != null){
                mAdapter.refreshList(musicEntities);
            }
        }else{
            mLoadStateView.loadFailed(R.string.common_local_song_empty);
        }
    }

    @Override
    protected boolean acceptNullResult() {
        return true;
    }
}
