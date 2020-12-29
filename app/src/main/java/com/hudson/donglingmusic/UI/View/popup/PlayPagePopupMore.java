package com.hudson.donglingmusic.UI.View.popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.activity.lyricsMake.LyricsMakeActivity;
import com.hudson.donglingmusic.common.Utils.CommonUtils;

/**
 * Created by Hudson on 2020/3/12.
 */
public class PlayPagePopupMore extends BasePopup {
    private OnLyricsAdjustListener mLyricsAdjustListener;

    public PlayPagePopupMore(Context context) {
        super(context);
    }

    @Override
    protected View initView(final Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.popup_more_play_page, null);
        root.findViewById(R.id.tv_make_lyrics).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LyricsMakeActivity.start(CommonUtils.contextThemeWrapperToActivity(context));
                dismiss();
            }
        });
        root.findViewById(R.id.tv_forward_lyrics).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(mLyricsAdjustListener != null){
                    mLyricsAdjustListener.onForward();
                }
            }
        });
        root.findViewById(R.id.tv_backward_lyrics).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(mLyricsAdjustListener != null){
                    mLyricsAdjustListener.onBackward();
                }
            }
        });
        return root;
    }

    public void setOnLyricsAdjustListener(OnLyricsAdjustListener lyricsAdjustListener) {
        mLyricsAdjustListener = lyricsAdjustListener;
    }
}
