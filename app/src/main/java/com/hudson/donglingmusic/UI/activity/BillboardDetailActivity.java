package com.hudson.donglingmusic.UI.activity;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.OnItemClickListener;
import com.hudson.donglingmusic.UI.View.LoadStateView;
import com.hudson.donglingmusic.UI.adapter.CommonMusicAdapter;
import com.hudson.donglingmusic.common.Utils.ToastUtils;
import com.hudson.donglingmusic.common.Utils.bitmapUtils.BitmapUtils;
import com.hudson.donglingmusic.entity.Billboard;
import com.hudson.donglingmusic.entity.MusicEntity;
import com.hudson.donglingmusic.global.DongLingApplication;
import com.hudson.donglingmusic.service.IPlayerController;

import java.util.List;

import static com.hudson.donglingmusic.common.Utils.bitmapUtils.BitmapUtils.MAX_BLUR;

/**
 * Created by Hudson on 2020/3/3.
 */
public class BillboardDetailActivity extends BaseLaunchLoadActivity<Billboard> implements OnItemClickListener<MusicEntity> {
    private static final String KEY_BILLBOARD_TYPE = "key_billboard_type";
    private int mBillboardType;
    private ImageView mSheetImg;
    private TextView mTitle,mTag,mDesc,mListenCount,mPlayAll;
    private View mSheetBg;
    private CommonMusicAdapter mAdapter;
    private LoadStateView mLoadStateView;

    @Override
    protected void initContentView(ConstraintLayout parent) {
        LayoutInflater.from(this).inflate(R.layout.activity_song_sheet_detail,parent);
        mSheetImg = (ImageView) parent.findViewById(R.id.iv_song_sheet);
        mTitle = (TextView) parent.findViewById(R.id.tv_title);
        mTag = (TextView) parent.findViewById(R.id.tv_tag);
        mDesc = (TextView) parent.findViewById(R.id.tv_desc);
        mPlayAll = (TextView) parent.findViewById(R.id.tv_play_all);
        mSheetBg = parent.findViewById(R.id.v_bg);
        mListenCount = (TextView) parent.findViewById(R.id.tv_listen_count);
        RecyclerView songList = (RecyclerView) parent.findViewById(R.id.rv_song_list);
        mLoadStateView = (LoadStateView) parent.findViewById(R.id.lsv_sheet_load);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        songList.setLayoutManager(manager);
        mAdapter = new CommonMusicAdapter();
        mAdapter.setOnItemClickListener(this);
        songList.setAdapter(mAdapter);
        disableLoadShow();
        mPlayAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSongSheet(0);
            }
        });
    }

    @Override
    protected Billboard requestData() {
        Billboard billboard = Billboard.fetchBillboard(mBillboardType, 0, 20);
        billboard.transformMusic();
        return billboard;
    }

    @Override
    protected void onDataFetchSuccess(final Billboard data) {
        mLoadStateView.loadSuccess();
        findViewById(R.id.v_tmp).setVisibility(View.GONE);
        Billboard.BillboardInfo billboardInfo = data.getBillboardInfo();
        String picUrl = billboardInfo.getPicS260();
        attachPic(picUrl);
        mTitle.setText(billboardInfo.getName());
//        String tag = data.getTag();
//        if(TextUtils.isEmpty(tag)){
//            mTag.setVisibility(View.GONE);
//        }else{
//            mTag.setVisibility(View.VISIBLE);
//            mTag.setText(tag);
//        }
        mDesc.setText(billboardInfo.getComment());
//        mListenCount.setText(String.valueOf(billboardInfo.get));

        List<MusicEntity> songs = data.getTransformMusics();
        mPlayAll.setText(getString(R.string.song_sheet_play_all,songs.size()));
        mAdapter.refreshList(songs);
    }

    private void attachPic(String picUrl){
        if(!TextUtils.isEmpty(picUrl)){
            Glide.with(this).load(picUrl).into(mSheetImg);
            BitmapUtils.loadNetBitmap(mSheetBg,picUrl, MAX_BLUR,true);
        }
    }

    @Override
    protected void onDataFetchFailed(Throwable e) {
        super.onDataFetchFailed(e);
        mLoadStateView.loadFailed();
    }

    @Override
    protected void prepare() {
        super.prepare();
        Intent intent = getIntent();
        if(intent != null){
            int sheetId = intent.getIntExtra(KEY_BILLBOARD_TYPE, -1);
            if(sheetId != -1){
                mBillboardType = sheetId;
            }else{
                invalidSongSheet();
            }
        }else{
            invalidSongSheet();
        }
    }

    private void invalidSongSheet(){
        ToastUtils.showToast(R.string.common_song_sheet_invalid);
        finish();
    }

    public static void start(Context from,int billboardType){
        Intent intent = new Intent(from, BillboardDetailActivity.class);
        intent.putExtra(KEY_BILLBOARD_TYPE,billboardType);
        from.startActivity(intent);
    }

    @Override
    public void onItemClick(MusicEntity item, int position) {
        // 点击了Item
        playSongSheet(position);
    }

    public void playSongSheet(int position) {
        IPlayerController controller = DongLingApplication.getPlayerController();
        if(controller != null){
            List<MusicEntity> songList = mAdapter.getDatas();
            if(songList.size() > 0){
                controller.setPlayList(songList,mData.generateSheetUniqueTag());
                controller.play(position);
            }else{
                ToastUtils.showToast(R.string.common_song_sheet_play_empty);
            }
        }
    }

}
