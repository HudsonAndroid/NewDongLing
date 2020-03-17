package com.hudson.donglingmusic.UI.Item.ViewHolder.lyricsMake;

import android.view.ViewGroup;

/**
 * Created by Hudson on 2020/3/11.
 */
public class EmptyViewHolder extends BaseLyricsMakeViewHolder {
    public EmptyViewHolder(ViewGroup parent, int height) {
        super(parent);
        mLyrics.getLayoutParams().height = height;
    }
}
