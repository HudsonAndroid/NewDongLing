package com.hudson.donglingmusic.UI.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.OnItemClickListener;
import com.hudson.donglingmusic.UI.Item.ViewHolder.AbsViewHolder;
import com.hudson.donglingmusic.UI.Item.ViewHolder.SheetDetailViewHolder;
import com.hudson.donglingmusic.entity.MusicEntity;

/**
 * Created by Hudson on 2020/3/4.
 */
public class CommonMusicAdapter extends RecyclerViewAdapter<MusicEntity> {
    private OnItemClickListener<MusicEntity> mListener;

    @Override
    protected AbsViewHolder<MusicEntity> getViewHolder(ViewGroup parent) {
        return new SheetDetailViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_viewholder_sheet_detail,parent,false),mListener);
    }

    public void setOnItemClickListener(OnItemClickListener<MusicEntity> listener) {
        mListener = listener;
    }
}
