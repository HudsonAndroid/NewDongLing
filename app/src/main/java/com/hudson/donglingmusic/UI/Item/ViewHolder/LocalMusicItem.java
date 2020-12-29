package com.hudson.donglingmusic.UI.Item.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.OnItemClickListener;
import com.hudson.donglingmusic.entity.MusicEntity;

/**
 * Created by Hudson on 2019/1/5.
 */
public class LocalMusicItem extends AbsViewHolder<MusicEntity> {
    private TextView mTextView;

    public LocalMusicItem(View itemView, OnItemClickListener<MusicEntity> listener) {
        super(itemView,listener);
        mTextView = itemView.findViewById(R.id.tv_title);
    }

    @Override
    public void refreshView(MusicEntity data) {
        super.refreshView(data);
        mTextView.setText(data.getTitle());
    }
}
