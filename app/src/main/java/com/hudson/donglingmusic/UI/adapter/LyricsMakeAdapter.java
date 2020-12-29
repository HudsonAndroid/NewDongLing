package com.hudson.donglingmusic.UI.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hudson.donglingmusic.UI.Item.ViewHolder.lyricsMake.BaseLyricsMakeViewHolder;
import com.hudson.donglingmusic.UI.Item.ViewHolder.lyricsMake.EmptyViewHolder;
import com.hudson.donglingmusic.UI.Item.ViewHolder.lyricsMake.LyricsMakeViewHolder;
import com.hudson.donglingmusic.UI.activity.lyricsMake.LyricsInputActivity;
import com.hudson.donglingmusic.common.Utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hudson on 2020/3/11.
 */
public class LyricsMakeAdapter extends RecyclerView.Adapter<BaseLyricsMakeViewHolder> {
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_BOTTOM = 2;
    private static final int TYPE_NORMAL = 3;
    private int mHalfHeight;
    private int mCompletePosition;
    protected final List<String> mDatas = new ArrayList<>();

    public void refreshList(List<String> datas){
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setHalfHeight(int halfHeight){
        mHalfHeight = halfHeight;
    }

    public LyricsMakeAdapter(){
        this(CommonUtils.getScreenHeight() / 3);
    }

    public LyricsMakeAdapter(int halfHeight) {
        mHalfHeight = halfHeight;
    }

    public void updateCompletePosition(int completePosition){
        mCompletePosition = completePosition;
        notifyDataSetChanged();
    }

    public boolean isEnd(int position){
        if(position > mDatas.size()){
            return true;
        }
        return false;
    }

    public String getLyrics(int position){
        position = position - getHeaderOffset();
        if(position >= 0 && position < mDatas.size()){
            return mDatas.get(position);
        }
        return "";
    }

    @NonNull
    public String getAllLyrics(){
        StringBuilder sb = new StringBuilder();
        for (String data : mDatas) {
            sb.append(data).append(LyricsInputActivity.LYRICS_DIVIDER);
        }
        return sb.toString();
    }

    @NonNull
    @Override
    public BaseLyricsMakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER || viewType == TYPE_BOTTOM){
            return new EmptyViewHolder(parent,mHalfHeight);
        }else{
            return new LyricsMakeViewHolder(parent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseLyricsMakeViewHolder holder, int position) {
        if(position > 0 && position < mDatas.size() + 1){
            holder.refreshView(mDatas.get(position-1),position <= mCompletePosition);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_HEADER;
        }else if(position == mDatas.size() + 1){
            return TYPE_BOTTOM;
        }else{
            return TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + 2;
    }

    public int getHeaderOffset(){
        return 1;
    }
}
