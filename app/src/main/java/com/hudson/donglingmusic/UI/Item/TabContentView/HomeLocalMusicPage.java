package com.hudson.donglingmusic.UI.Item.TabContentView;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.OnItemClickListener;
import com.hudson.donglingmusic.UI.View.TabView.ILoadDataView;
import com.hudson.donglingmusic.UI.adapter.LocalMusicAdapter;
import com.hudson.donglingmusic.service.IPlayerController;
import com.hudson.donglingmusic.service.MusicService;
import com.hudson.localMusic.SystemMusicOpenHelper;
import com.hudson.musicentitylib.MusicEntity;

import java.util.ArrayList;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by Hudson on 2019/1/5.
 */
public class HomeLocalMusicPage extends LinearLayout implements ILoadDataView, OnItemClickListener<MusicEntity> {
    private LocalMusicAdapter mAdapter;
    private IPlayerController mPlayerController;
    private Context mContext;
    private ServiceConnectionEntity mConn;

    public HomeLocalMusicPage(Context context) {
        this(context, null);
    }

    public HomeLocalMusicPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeLocalMusicPage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        bindService(mContext);
        LayoutInflater.from(context).inflate(R.layout.page_home_local_music, this, true);
        RecyclerView recyclerView = findViewById(R.id.rv_list);
        LinearLayoutManager layout = new LinearLayoutManager(context);
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layout);
        mAdapter = new LocalMusicAdapter(context,this);
        recyclerView.setAdapter(mAdapter);
    }

    private void bindService(Context context){
        Intent intent = new Intent(context,MusicService.class);
        mConn = new ServiceConnectionEntity();
        context.bindService(intent, mConn,BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(MusicEntity item,int position) {
        mPlayerController.play(position);
    }

    private class ServiceConnectionEntity implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPlayerController = (IPlayerController) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mContext.unbindService(mConn);
    }

    @Override
    public void loadData() {
        ArrayList<MusicEntity> musicEntities = SystemMusicOpenHelper.queryMusics(getContext());
        mAdapter.refreshList(musicEntities);
        mPlayerController.setPlayList(musicEntities);
    }
}
