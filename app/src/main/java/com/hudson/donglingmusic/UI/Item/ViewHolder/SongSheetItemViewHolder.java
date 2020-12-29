package com.hudson.donglingmusic.UI.Item.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.adapter.SongSheetPageAdapter;
import com.hudson.donglingmusic.common.Utils.StringUtils;
import com.hudson.donglingmusic.entity.SongSheet;

/**
 * Created by Hudson on 2020/3/2.
 */
public class SongSheetItemViewHolder extends AbsViewHolder<SongSheet.SheetItem> {
    private ImageView mBg;
    private TextView mListenCount,mTag,mTitle;
    private String mListId;
    private String mImgUrl;

    public SongSheetItemViewHolder(View itemView, final SongSheetPageAdapter.OnItemClickListener listener) {
        super(itemView);
        mBg = (ImageView) itemView.findViewById(R.id.iv_gedan_bg);
        mListenCount = (TextView) itemView.findViewById(R.id.tv_listen_count);
        mTag = (TextView) itemView.findViewById(R.id.tv_gedan_tag);
        mTitle = (TextView) itemView.findViewById(R.id.tv_gedan_title);
        itemView.findViewById(R.id.v_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onItemClick(mBg,mListId,mImgUrl);
                }
            }
        });
    }

    @Override
    public void refreshView(SongSheet.SheetItem data) {
        String title = data.getTitle();
//        Object itemTag = mBg.getTag();
        mListId = String.valueOf(data.getListId());
//        if(itemTag==null||!itemTag.equals(title)){
        mImgUrl = data.getPic300();
        Glide.with(mBg.getContext()).load(mImgUrl).into(mBg);
//            mBg.setTag(title);
            mListenCount.setText(StringUtils.getHumanNum(data.getListenNum()));
            mTag.setText(data.getTag());
            mTitle.setText(title);
//        }
    }
}
