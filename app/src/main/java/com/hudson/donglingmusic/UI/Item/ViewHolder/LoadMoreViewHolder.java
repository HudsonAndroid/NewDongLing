package com.hudson.donglingmusic.UI.Item.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.hudson.donglingmusic.R;

/**
 * Created by Hudson on 2020/3/2.
 */
public class LoadMoreViewHolder extends AbsViewHolder<Integer>{
    public static final int STATUS_HAS_MORE = 0;
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_NO_MORE = 2;
    public static final int STATUS_LOAD_FAILED = 3;
    private View mProgressBar;
    private TextView mTips;

    public LoadMoreViewHolder(View itemView) {
        super(itemView);
        mProgressBar = itemView.findViewById(R.id.pb_loading);
        mTips = (TextView) itemView.findViewById(R.id.tv_tips);
    }

    @Override
    public void refreshView(Integer type) {
        switch (type){
            case STATUS_HAS_MORE:
                mProgressBar.setVisibility(View.GONE);
                mTips.setText(R.string.common_load_more);
                break;
            case STATUS_LOADING:
                mProgressBar.setVisibility(View.VISIBLE);
                mTips.setText(R.string.common_loading);
                break;
            case STATUS_NO_MORE:
                mProgressBar.setVisibility(View.GONE);
                mTips.setText(R.string.common_no_more);
                break;
            case STATUS_LOAD_FAILED:
                mProgressBar.setVisibility(View.GONE);
                mTips.setText(R.string.common_load_failed);
                break;
        }
    }
}
