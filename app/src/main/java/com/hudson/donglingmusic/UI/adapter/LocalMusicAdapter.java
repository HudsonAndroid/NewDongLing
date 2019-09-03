package com.hudson.donglingmusic.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.OnItemClickListener;
import com.hudson.donglingmusic.UI.Item.ViewHolder.AbsViewHolder;
import com.hudson.donglingmusic.UI.Item.ViewHolder.LocalMusicItem;
import com.hudson.musicentitylib.MusicEntity;

/**
 * Created by Hudson on 2019/1/5.
 */
public class LocalMusicAdapter extends RecyclerViewAdapter<MusicEntity> {
    private OnItemClickListener<MusicEntity> mClickListener;

    public LocalMusicAdapter(Context context,OnItemClickListener<MusicEntity> listener) {
        super(context);
        mClickListener = listener;
    }

    @Override
    protected AbsViewHolder<MusicEntity> getViewHolder(Context context, ViewGroup parent) {
        return new LocalMusicItem(
                LayoutInflater.from(context).inflate(R.layout.item_local_music,parent,false)
                ,mClickListener);
    }
}
