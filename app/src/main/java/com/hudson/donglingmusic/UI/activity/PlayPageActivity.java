package com.hudson.donglingmusic.UI.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.audiofx.Visualizer;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hudson.cirlcevisiblemusic.CircleVisibleView;
import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.View.LyricsView.LyricsViewManager;
import com.hudson.donglingmusic.UI.View.PlayProgressView.IProgressView;
import com.hudson.donglingmusic.UI.View.PlayProgressView.PlayProgressManager;
import com.hudson.donglingmusic.UI.View.popup.OnLyricsAdjustListener;
import com.hudson.donglingmusic.UI.View.popup.PlayPagePopupMore;
import com.hudson.donglingmusic.common.Utils.PermissionUtils.PermissionCallback;
import com.hudson.donglingmusic.common.Utils.PermissionUtils.PermissionUtils;
import com.hudson.donglingmusic.common.Utils.ToastUtils;
import com.hudson.donglingmusic.common.Utils.asyncUtils.AsyncException;
import com.hudson.donglingmusic.common.Utils.asyncUtils.AsyncTask;
import com.hudson.donglingmusic.common.Utils.asyncUtils.CommonOnFail;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoInBackground;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoOnFail;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoOnSuccess;
import com.hudson.donglingmusic.common.Utils.bitmapUtils.BitmapUtils;
import com.hudson.donglingmusic.controller.ModuleManager;
import com.hudson.donglingmusic.entity.MusicEntity;
import com.hudson.donglingmusic.persistent.db.interfaces.IUserDb;
import com.hudson.donglingmusic.service.IPlayerController;
import com.hudson.donglingmusic.service.listener.OnMusicChangedListener;

/**
 * 播放页面
 * Created by Hudson on 2019/1/20.
 */
public class PlayPageActivity extends BasePlayActivity implements OnMusicChangedListener, OnLyricsAdjustListener {
    public static final int REQUEST_MAKE_LYRICS_CODE = 0x969;
    private Visualizer mVisualizer;
    private CircleVisibleView mVisibleView;
//    private CircleVisibleMusicView mCircleVisibleView;
    private RelativeLayout mLyricsContainer;
    private LyricsViewManager mLyricsViewManager;
    private TextView mTitle,mArtist;
    private View mContainer;
    private IProgressView mProgressView;
    private PlayPagePopupMore mMorePopup;
    private ImageView mFavorite;
    private AsyncTask<Boolean> mFavoriteSwitcher;

    @Override
    public void onPlayerControllerInitSuccess(IPlayerController controller) {
        super.onPlayerControllerInitSuccess(controller);
        init();
        PlayProgressManager.getInstance().addProgressView(mProgressView);
        mLyricsViewManager = new LyricsViewManager(mLyricsContainer);
        updateFavoriteTag();
        final ImageView playState = findViewById(R.id.iv_play_mode);
        playState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int drawableId = mPlayerController.switchPlayState();
                playState.setImageResource(drawableId);
            }
        });
        playState.setImageResource(mPlayerController.getPlayState().getDrawableId());
        findViewById(R.id.iv_play_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPlayerController.getPlayList().size() > 0){
                    PlayQueueActivity.start(PlayPageActivity.this);
                }else{
                    ToastUtils.showToast(R.string.common_play_list_empty);
                }
            }
        });
    }

    @Override
    protected void initView(ConstraintLayout parent) {
        LayoutInflater.from(this).inflate(R.layout.activity_play_page,parent);
        mContainer = findViewById(R.id.cl_container);
        mLyricsContainer = findViewById(R.id.rl_lyrics_container);
        mVisibleView = findViewById(R.id.cvv_container);
        mProgressView = findViewById(R.id.pp_progress);
//        mCircleVisibleView = findViewById(R.id.cvm_music);
        mTitle = findViewById(R.id.tv_title);
        mArtist = findViewById(R.id.tv_artist);
        findViewById(R.id.iv_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
            }
        });
        findViewById(R.id.iv_pre).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPre();
            }
        });
        findViewById(R.id.iv_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMorePopup == null){
                    mMorePopup = new PlayPagePopupMore(PlayPageActivity.this);
                    mMorePopup.setOnLyricsAdjustListener(PlayPageActivity.this);
                }
                mMorePopup.show(v);
            }
        });
        mFavorite = (ImageView) findViewById(R.id.iv_add_favorite);
        mFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavorite();
            }
        });
    }

    private void toggleFavorite(){
        if(mPlayerController != null){
            if(mFavoriteSwitcher == null){
                mFavoriteSwitcher = new AsyncTask<Boolean>().useDbThreadPool().doInBackground(new IDoInBackground<Boolean>() {
                    @Override
                    public Boolean run() throws AsyncException {
                        boolean isFavorite = (boolean) mFavorite.getTag();
                        IUserDb userDb = ModuleManager.getDbModule().getUserDb();
                        MusicEntity curMusic = mPlayerController.getCurMusic();
                        if(curMusic != null){
                            if(!isFavorite){
                                if(userDb.addFavoriteMusic(curMusic)){
                                    isFavorite = true;
                                }
                            }else{
                                if(userDb.removeFromFavorite(curMusic)){
                                    isFavorite = false;
                                }
                            }
                            return isFavorite;
                        }
                        return null;
                    }
                }).doOnSuccess(new IDoOnSuccess<Boolean>() {
                    @Override
                    public void onSuccess(Boolean isFavorite) {
                        if(isFavorite){
                            ToastUtils.showToast(R.string.add_favorite_success);
                            mFavorite.setImageResource(R.drawable.icon_add_favorite_already);
                            mFavorite.setTag(true);
                        }else{
                            ToastUtils.showToast(R.string.remove_favorite_success);
                            mFavorite.setImageResource(R.drawable.icon_add_favorite);
                            mFavorite.setTag(false);
                        }
                    }
                }).doOnFail(new IDoOnFail() {
                    @Override
                    public void onFail(Throwable e) {
                        e.printStackTrace();

                    }
                }).forbidAutoClear();
            }
            mFavoriteSwitcher.execute();
        }
    }

    private void updateFavoriteTag(){
        if(mPlayerController != null && mPlayerController.getCurMusic() != null){
            new AsyncTask<Boolean>().useDbThreadPool().doInBackground(new IDoInBackground<Boolean>() {
                @Override
                public Boolean run() throws AsyncException {
                    return ModuleManager.getDbModule()
                            .getUserDb().isFavoriteMusic(mPlayerController.getCurMusic());
                }
            }).doOnSuccess(new IDoOnSuccess<Boolean>() {
                @Override
                public void onSuccess(Boolean isFavorite) {
                    if(isFavorite){
                        mFavorite.setImageResource(R.drawable.icon_add_favorite_already);
                        mFavorite.setTag(true);
                    }else{
                        mFavorite.setImageResource(R.drawable.icon_add_favorite);
                        mFavorite.setTag(false);
                    }
                }
            }).doOnFail(new CommonOnFail(){
                @Override
                protected void handleOnFail(Throwable e) {
                    super.handleOnFail(e);
                    mFavorite.setImageResource(R.drawable.icon_add_favorite);
                }
            }).execute();
        }
    }

    private void init(){
        refreshMusicInfo();
        final int audioSessionId = mPlayerController.getAudioSessionId();
        if(audioSessionId != -1){
            PermissionUtils.request(PlayPageActivity.this, new PermissionCallback() {
                @Override
                public void onSuccess(Activity activity) {
                    startCollectAudio(audioSessionId);
                }

                @Override
                public void onFailed(Activity activity) {
                    ToastUtils.showToast(R.string.common_no_permission_for_visible_music);
                }
            }, Manifest.permission.RECORD_AUDIO);
        }
    }

    private void startCollectAudio(int audioSessionId) {
        mVisualizer = new Visualizer(audioSessionId);
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        //参数三是采样频率
        mVisualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform,
                                              int samplingRate) {
//                    mVisibleView.updateVisualizer(waveform);
            }

            //这个回调采集的是快速傅里叶变换有关的数据,fft就是采集到的byte数据（频域波形图）
            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] fft,
                                         int samplingRate) {
                mVisibleView.updateVisualizer(fft);
            }
        }, Visualizer.getMaxCaptureRate()/2, true, true);
        mVisualizer.setEnabled(true);
    }

    @Override
    protected void refreshMusicInfo() {
        super.refreshMusicInfo();
        final MusicEntity curMusic = mPlayerController.getCurMusic();
        if(curMusic != null){
            mTitle.setText(curMusic.getTitle());
            mArtist.setText(curMusic.getArtist());
//            MusicPicUtils.loadMusicPic(curMusic,mContainer,R.drawable.default_bg,
//                    BitmapUtils.GAUSSIAN_BLUR_RADIUS_MAX,true);
            BitmapUtils.loadMusicPic(curMusic,mContainer,25,true,R.drawable.default_bg);
            updateFavoriteTag();
        }
    }

    @Nullable
    @Override
    protected ImageView getPlayPauseImageView() {
        return (ImageView) findViewById(R.id.iv_play_pause);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUEST_MAKE_LYRICS_CODE){
            mLyricsViewManager.reloadLyrics();
        }
    }

    @Override
    protected void onDestroy() {
        if(mVisualizer!=null){
            mVisualizer.setEnabled(false);
            mVisualizer.release();
            mVisualizer = null;
        }
        if(mFavoriteSwitcher != null){
            mFavoriteSwitcher.clear();
        }
        PlayProgressManager.getInstance().removeProgressView(mProgressView);
        mLyricsViewManager.onDestroy();
        super.onDestroy();
    }

    public static void start(Context from){
        from.startActivity(new Intent(from,PlayPageActivity.class));
    }

    @Override
    public void onForward() {
        mLyricsViewManager.forward();
    }

    @Override
    public void onBackward() {
        mLyricsViewManager.backward();
    }
}
