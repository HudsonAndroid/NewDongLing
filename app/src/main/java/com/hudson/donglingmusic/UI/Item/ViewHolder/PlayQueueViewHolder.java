package com.hudson.donglingmusic.UI.Item.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.OnItemClickListener;
import com.hudson.donglingmusic.entity.MusicEntity;

/**
 * Created by Hudson on 2020/3/10.
 */
public class PlayQueueViewHolder extends AbsViewHolder<MusicEntity> {
    private TextView mTitle,mArtist;

    public PlayQueueViewHolder(View itemView,
                               OnItemClickListener<MusicEntity> listener,
                               final OnItemDeleteInvoke deleteListener) {
        super(itemView,listener);
        mTitle = (TextView) itemView.findViewById(R.id.tv_title);
        mArtist = (TextView) itemView.findViewById(R.id.tv_artist);
        itemView.findViewById(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deleteListener != null){
                    deleteListener.onItemDelete(getAdapterPosition());
                }
            }
        });
    }

    @Override
    public void refreshView(MusicEntity data) {
        super.refreshView(data);
        mTitle.setText(data.getTitle());
        mArtist.setText(data.getArtist());
    }

    public interface OnItemDeleteInvoke{
        void onItemDelete(int position);
    }
}
