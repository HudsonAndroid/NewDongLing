package com.hudson.donglingmusic.UI.activity;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.OnItemClickListener;
import com.hudson.donglingmusic.UI.Item.ViewHolder.PlayQueueViewHolder;
import com.hudson.donglingmusic.UI.adapter.PlayQueueAdapter;
import com.hudson.donglingmusic.entity.MusicEntity;
import com.hudson.donglingmusic.service.IPlayerController;

import java.util.List;

/**
 * Created by Hudson on 2020/3/10.
 */
public class PlayQueueActivity extends BasePlayActivity implements OnItemClickListener<MusicEntity>, PlayQueueViewHolder.OnItemDeleteInvoke {
    private RecyclerView mList;
    private TextView mCount,mPlayMode;
    private List<MusicEntity> mPlayList;
    private PlayQueueAdapter mAdapter;

    @Override
    protected void initView(ConstraintLayout parent) {
        LayoutInflater.from(this).inflate(R.layout.activity_play_queue,parent);
        findViewById(R.id.cl_outer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePlayQueue();
            }
        });
        mList = (RecyclerView) parent.findViewById(R.id.rv_queue);
        mCount = (TextView) parent.findViewById(R.id.tv_count);
        mPlayMode = (TextView) parent.findViewById(R.id.tv_play_mode);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager);
    }

    @Override
    public void onPlayerControllerInitSuccess(IPlayerController controller) {
        super.onPlayerControllerInitSuccess(controller);
        mPlayMode.setText(mPlayerController.getPlayState().getStrId());
        mPlayList = mPlayerController.getPlayList();
        mCount.setText(getString(R.string.queue_play_current, mPlayList.size()));
        mAdapter = new PlayQueueAdapter(this, this);
        mList.setAdapter(mAdapter);
        mAdapter.refreshList(mPlayList);
    }

    @Override
    public void onItemClick(MusicEntity item, int position) {
        mPlayerController.play(position);
        closePlayQueue();
    }

    private void closePlayQueue() {
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public void onBackPressed() {
        closePlayQueue();
    }

    @Override
    public void onItemDelete(int position) {
        MusicEntity curMusic = mPlayerController.getCurMusic();
        MusicEntity musicEntity = mPlayList.get(position);
        if(curMusic != musicEntity){
            mPlayList.remove(musicEntity);
            mAdapter.refreshList(mPlayList);
        }else{
            // TODO: 2020/3/10 删除正在播放的歌曲
        }
    }

    public static void start(Context from){
        from.startActivity(new Intent(from,PlayQueueActivity.class));
    }
}
