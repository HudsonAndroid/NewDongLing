package com.hudson.donglingmusic.UI.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hudson.donglingmusic.UI.Item.ViewHolder.AbsViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView的统一adapter
 * Created by hpz on 2018/5/11.
 */

public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<AbsViewHolder<T>> {
    protected List<T> mDatas = new ArrayList<>();
    protected Context mContext;

    protected RecyclerViewAdapter(Context context){
        mContext = context;
    }

    public void refreshList(List<T> datas){
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public AbsViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        return getViewHolder(mContext,parent);
    }

    @Override
    public void onBindViewHolder(AbsViewHolder<T> holder, int position) {
        holder.refreshView(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    protected abstract AbsViewHolder<T> getViewHolder(Context context,ViewGroup parent);
}
