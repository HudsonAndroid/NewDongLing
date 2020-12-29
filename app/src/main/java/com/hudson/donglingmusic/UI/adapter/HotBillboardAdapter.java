package com.hudson.donglingmusic.UI.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.OnItemClickListener;
import com.hudson.donglingmusic.UI.Item.ViewHolder.AbsViewHolder;
import com.hudson.donglingmusic.UI.Item.ViewHolder.BillboardViewHolder;
import com.hudson.donglingmusic.entity.Billboard;

/**
 * Created by Hudson on 2020/3/19.
 */
public class HotBillboardAdapter extends RecyclerViewAdapter<Billboard> {
    private final OnItemClickListener<Billboard> listener;

    public HotBillboardAdapter(OnItemClickListener<Billboard> listener) {
        this.listener = listener;
    }

    @Override
    protected AbsViewHolder<Billboard> getViewHolder(ViewGroup parent) {
        return new BillboardViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_holder_billboard,parent,false), listener);
    }
}
