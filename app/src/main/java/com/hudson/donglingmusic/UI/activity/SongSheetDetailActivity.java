package com.hudson.donglingmusic.UI.activity;

import android.app.Activity;
import android.app.ActivityOptions;
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
import com.hudson.donglingmusic.entity.MusicEntity;
import com.hudson.donglingmusic.entity.SheetDetail;
import com.hudson.donglingmusic.global.DongLingApplication;
import com.hudson.donglingmusic.service.IPlayerController;

import java.util.List;

import static com.hudson.donglingmusic.common.Utils.bitmapUtils.BitmapUtils.MAX_BLUR;


/**
 * Created by Hudson on 2020/3/3.
 */
public class SongSheetDetailActivity extends BaseLaunchLoadActivity<SheetDetail> implements OnItemClickListener<MusicEntity> {
    private static final String TRANSITION_TAG = "sheet_img";
    private static final String KEY_SONG_SHEET_ID = "key_song_sheet_id";
    private static final String KEY_SHEET_IMG_URL = "key_sheet_img";
    private String mSongSheetId;
    private ImageView mSheetImg;
    private TextView mTitle,mTag,mDesc,mListenCount,mPlayAll;
    private boolean mIsTransitionLoad = false;
    private View mSheetBg;
    private CommonMusicAdapter mAdapter;
    private LoadStateView mLoadStateView;
    private String mTransitionPicUrl;

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
        Intent intent = getIntent();
        if(intent != null){
            mTransitionPicUrl = intent.getStringExtra(KEY_SHEET_IMG_URL);
            if(!TextUtils.isEmpty(mTransitionPicUrl)){
                mIsTransitionLoad = true;
                Glide.with(this).load(mTransitionPicUrl).into(mSheetImg);
            }
        }
        disableLoadShow();
        mPlayAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSongSheet(0);
            }
        });
    }

    @Override
    protected SheetDetail requestData() {
        return SheetDetail.fetchData(mSongSheetId);
    }

    @Override
    protected void onDataFetchSuccess(final SheetDetail data) {
        mLoadStateView.loadSuccess();
        findViewById(R.id.v_tmp).setVisibility(View.GONE);
        String picUrl = data.getPic300();
        attachPic(picUrl);
        mTitle.setText(data.getTitle());
        String tag = data.getTag();
        if(TextUtils.isEmpty(tag)){
            mTag.setVisibility(View.GONE);
        }else{
            mTag.setVisibility(View.VISIBLE);
            mTag.setText(tag);
        }
        mDesc.setText(data.getDesc());
        mListenCount.setText(String.valueOf(data.getListenCount()));
        List<MusicEntity> songs = data.getSongs();
        mPlayAll.setText(getString(R.string.song_sheet_play_all,songs.size()));
        mAdapter.refreshList(songs);
    }

    private void attachPic(String picUrl){
        if(mIsTransitionLoad && !TextUtils.isEmpty(mTransitionPicUrl)){
            picUrl = mTransitionPicUrl;
        }else{
            if(!TextUtils.isEmpty(picUrl)){
                Glide.with(this).load(picUrl).into(mSheetImg);
            }
        }
        if(!TextUtils.isEmpty(picUrl)){
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
            String sheetId = intent.getStringExtra(KEY_SONG_SHEET_ID);
            if(!TextUtils.isEmpty(sheetId)){
                mSongSheetId = sheetId;
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

    public static void startWithTransition(Activity from, View targetView,String songSheetId, String imgUrl){
        if (TextUtils.isEmpty(imgUrl)) {
            start(from,songSheetId);
        }else{
            Intent intent = new Intent(from, SongSheetDetailActivity.class);
            intent.putExtra(KEY_SONG_SHEET_ID,songSheetId);
            intent.putExtra(KEY_SHEET_IMG_URL,imgUrl);
            from.startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(from,targetView,TRANSITION_TAG).toBundle());
        }
    }

    public static void start(Context from,String songSheetId){
        Intent intent = new Intent(from, SongSheetDetailActivity.class);
        intent.putExtra(KEY_SONG_SHEET_ID,songSheetId);
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
