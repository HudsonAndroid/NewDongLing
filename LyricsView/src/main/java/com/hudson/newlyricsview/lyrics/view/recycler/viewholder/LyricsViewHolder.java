package com.hudson.newlyricsview.lyrics.view.recycler.viewholder;

import android.view.View;

import com.hudson.newlyricsview.lyrics.entity.Lyrics;
import com.hudson.newlyricsview.lyrics.view.item.LyricsTextView;

/**
 * Created by hpz on 2018/5/11.
 */

public class LyricsViewHolder extends BaseViewHolder {
    private LyricsTextView mContent;
    private int mFocusColor;
    private int mNormalColor;

    public LyricsViewHolder(View itemView,int focusColor,int normalColor) {
        super(itemView);
        mContent = (LyricsTextView) itemView;
        mFocusColor = focusColor;
        mNormalColor = normalColor;
    }

    @Override
    public void refreshView(Lyrics data, boolean isCurrent) {
        mContent.setLyrics(data);
        if(isCurrent){
            mContent.setTextColor(mFocusColor);
        }else{
            mContent.setTextColor(mNormalColor);
        }
    }
}
