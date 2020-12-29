package com.hudson.donglingmusic.UI.Item.ViewHolder.homeMine;

import android.view.View;

import com.android.library.YLCircleImageView;
import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.ViewHolder.AbsViewHolder;
import com.hudson.donglingmusic.UI.activity.SongSheetDetailActivity;
import com.hudson.donglingmusic.entity.HomeMineData;

import static com.hudson.donglingmusic.entity.SheetDetail.DEFAULT_FAVORITE_SHEET_ID;

/**
 * Created by Hudson on 2020/3/21.
 */
public class HeaderViewHolder extends AbsViewHolder<HomeMineData.Header> {
    private YLCircleImageView mFavorite;

    public HeaderViewHolder(final View itemView) {
        super(itemView);
        mFavorite = (YLCircleImageView) itemView.findViewById(R.id.iv_favorite);
        mFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongSheetDetailActivity.start(itemView.getContext(),DEFAULT_FAVORITE_SHEET_ID);
            }
        });
    }
}
