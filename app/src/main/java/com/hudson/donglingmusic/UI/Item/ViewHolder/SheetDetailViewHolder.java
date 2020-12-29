package com.hudson.donglingmusic.UI.Item.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.OnItemClickListener;
import com.hudson.donglingmusic.entity.MusicEntity;

/**
 * Created by Hudson on 2020/3/4.
 */
public class SheetDetailViewHolder extends AbsViewHolder<MusicEntity> {
    private TextView mTitle,mNumber,mInfo;
    private View mMore;

    public SheetDetailViewHolder(View itemView, OnItemClickListener<MusicEntity> listener) {
        super(itemView,listener);
        mTitle = (TextView) itemView.findViewById(R.id.tv_title);
        mNumber = (TextView) itemView.findViewById(R.id.tv_number);
        mInfo = (TextView) itemView.findViewById(R.id.tv_info);
        mMore = itemView.findViewById(R.id.iv_more);
    }

    @Override
    public void refreshView(MusicEntity data) {
        super.refreshView(data);
        mTitle.setText(data.getTitle());
        mNumber.setText(String.valueOf(getAdapterPosition()+1));
        mInfo.setText(data.getArtist()+"-"+data.getAlbumTitle());
        mMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击了更多
            }
        });
    }
}
