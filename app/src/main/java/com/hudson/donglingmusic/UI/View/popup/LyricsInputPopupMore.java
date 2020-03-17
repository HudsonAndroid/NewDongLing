package com.hudson.donglingmusic.UI.View.popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.hudson.donglingmusic.R;

/**
 * Created by Hudson on 2020/3/17.
 */
public class LyricsInputPopupMore extends BasePopup {
    private OnMenuClickListener mOnMenuClickListener;

    public LyricsInputPopupMore(Context context,OnMenuClickListener listener) {
        super(context);
        mOnMenuClickListener = listener;
    }

    @Override
    protected View initView(final Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.popup_more_lyrics_input, null);
        root.findViewById(R.id.tv_filter_lyrics).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnMenuClickListener != null){
                    mOnMenuClickListener.onFilterClick();
                }
                dismiss();
            }
        });
        return root;
    }

    public interface OnMenuClickListener{
        void onFilterClick();
    }
}
