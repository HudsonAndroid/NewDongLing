package com.hudson.donglingmusic.UI.Item.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hudson.donglingmusic.UI.Item.OnItemClickListener;

/**
 * 抽象的ViewHolder
 * Created by hpz on 2018/5/11.
 */

public abstract class AbsViewHolder<T> extends RecyclerView.ViewHolder {
    protected T mData = null;

    public AbsViewHolder(View itemView) {
        super(itemView);
    }

    public AbsViewHolder(View itemView, final OnItemClickListener<T> listener){
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(mData,getAdapterPosition());
            }
        });
    }

    public void refreshView(T data){
        mData = data;
    }
}
