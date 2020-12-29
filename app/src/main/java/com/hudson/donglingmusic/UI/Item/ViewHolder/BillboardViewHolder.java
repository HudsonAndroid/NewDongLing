package com.hudson.donglingmusic.UI.Item.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.OnItemClickListener;
import com.hudson.donglingmusic.entity.Billboard;
import com.hudson.donglingmusic.entity.NetSongInfo;

import java.util.List;

/**
 * Created by Hudson on 2020/3/19.
 */
public class BillboardViewHolder extends AbsViewHolder<Billboard> {
    private TextView mTitle,mFirstListen,mSecondListen,mThirdListen,mFirstSong,mSecondSong,mThirdSong;
    private ImageView mFirst,mSecond,mThird;

    public BillboardViewHolder(View itemView,final OnItemClickListener<Billboard> listener) {
        super(itemView,listener);
        mTitle = (TextView) itemView.findViewById(R.id.tv_title);
        mFirst = (ImageView) itemView.findViewById(R.id.iv_first);
        mSecond = (ImageView) itemView.findViewById(R.id.iv_second);
        mThird = (ImageView) itemView.findViewById(R.id.iv_third);
        mFirstListen = (TextView) itemView.findViewById(R.id.tv_first_listen);
        mSecondListen = (TextView) itemView.findViewById(R.id.tv_second_listen);
        mThirdListen = (TextView) itemView.findViewById(R.id.tv_third_listen);
        mFirstSong = (TextView) itemView.findViewById(R.id.tv_first_song);
        mSecondSong = (TextView) itemView.findViewById(R.id.tv_second_song);
        mThirdSong = (TextView) itemView.findViewById(R.id.tv_third_song);
    }

    @Override
    public void refreshView(Billboard data) {
        super.refreshView(data);
        Billboard.BillboardInfo billboardInfo = data.getBillboardInfo();
        mTitle.setText(billboardInfo.getName());
        List<NetSongInfo> netSongInfos = data.getNetSongInfos();
        if(netSongInfos!= null && netSongInfos.size() >= 3){
            attach(mFirst,mFirstListen,mFirstSong,netSongInfos.get(0));
            attach(mSecond,mSecondListen,mSecondSong,netSongInfos.get(1));
            attach(mThird,mThirdListen,mThirdSong,netSongInfos.get(2));
        }
    }

    private void attach(ImageView pic,TextView listen,TextView song,NetSongInfo netSongInfo){
        Glide.with(pic.getContext()).load(netSongInfo.getPicPremium()).into(pic);
        listen.setText(String.valueOf(netSongInfo.getHotPoint()));
        song.setText(netSongInfo.getTitle());
    }
}
