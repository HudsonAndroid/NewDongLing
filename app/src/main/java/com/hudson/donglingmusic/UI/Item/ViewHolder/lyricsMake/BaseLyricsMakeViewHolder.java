package com.hudson.donglingmusic.UI.Item.ViewHolder.lyricsMake;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.ViewHolder.AbsViewHolder;

/**
 * Created by Hudson on 2020/3/12.
 */
public class BaseLyricsMakeViewHolder extends AbsViewHolder<String> {
    protected TextView mLyrics;
    private int mCompleteColor;
    private int mNormalColor;

    public BaseLyricsMakeViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_viewholder_lyrics_make,parent,false));
        mLyrics = (TextView) itemView.findViewById(R.id.tv_lyrics);
        Resources resources = mLyrics.getContext().getResources();
        mCompleteColor = resources.getColor(R.color.common_lyrics_make_complete);
        mNormalColor = resources.getColor(R.color.common_lyrics_make_normal);
    }

    public void refreshView(String data,boolean isComplete){
        refreshView(data);
        if(isComplete){
            mLyrics.setTextColor(mCompleteColor);
        }else{
            mLyrics.setTextColor(mNormalColor);
        }
    }

    @Override
    public final void refreshView(String data) {
        super.refreshView(data);
        mLyrics.setText(data);
    }
}
