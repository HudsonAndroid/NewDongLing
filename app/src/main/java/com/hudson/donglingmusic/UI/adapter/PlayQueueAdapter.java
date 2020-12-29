package com.hudson.donglingmusic.UI.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.OnItemClickListener;
import com.hudson.donglingmusic.UI.Item.ViewHolder.AbsViewHolder;
import com.hudson.donglingmusic.UI.Item.ViewHolder.PlayQueueViewHolder;
import com.hudson.donglingmusic.entity.MusicEntity;

/**
 * Created by Hudson on 2020/3/10.
 */
public class PlayQueueAdapter extends RecyclerViewAdapter<MusicEntity> {
    private OnItemClickListener<MusicEntity> mListener;
    private PlayQueueViewHolder.OnItemDeleteInvoke mDeleteListener;

    public PlayQueueAdapter(OnItemClickListener<MusicEntity> listener,
                            PlayQueueViewHolder.OnItemDeleteInvoke deleteListener) {
        mListener = listener;
        mDeleteListener = deleteListener;
    }

    @Override
    protected AbsViewHolder<MusicEntity> getViewHolder(ViewGroup parent) {
        return new PlayQueueViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_viewholder_play_queue,parent,
                false),mListener,mDeleteListener);
    }
}
