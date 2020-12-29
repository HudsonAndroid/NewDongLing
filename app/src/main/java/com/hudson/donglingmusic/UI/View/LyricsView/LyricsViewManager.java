package com.hudson.donglingmusic.UI.View.LyricsView;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;

import com.hudson.donglingmusic.UI.View.LyricsView.emptyLyrics.DongLingEmptyLyricsView;
import com.hudson.donglingmusic.common.Utils.asyncUtils.AsyncException;
import com.hudson.donglingmusic.common.Utils.asyncUtils.AsyncTask;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoInBackground;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoOnFail;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoOnSuccess;
import com.hudson.donglingmusic.common.config.ConfigManager;
import com.hudson.donglingmusic.entity.LyricsResult;
import com.hudson.donglingmusic.entity.MusicEntity;
import com.hudson.donglingmusic.global.DongLingApplication;
import com.hudson.donglingmusic.service.IPlayerController;
import com.hudson.donglingmusic.service.listener.OnMusicChangedListener;
import com.hudson.newlyricsview.lyrics.LyricsController;
import com.hudson.newlyricsview.lyrics.view.config.LyricsViewConfig;
import com.hudson.newlyricsview.lyrics.view.locateProgress.ILocateProgressListener;
import com.hudson.newlyricsview.lyrics.view.style.LyricsViewStyle;

/**
 * Created by Hudson on 2020/3/8.
 */
public class LyricsViewManager implements OnMusicChangedListener {
    private static final int LYRICS_ADJUST_TIME = 500;
    private LyricsController mLyricsController;
    private ViewGroup mContainer;
    private IPlayerController mPlayerController;
    private boolean mHasLoaded = false;

    public LyricsViewManager(ViewGroup parent) {
        mContainer = parent;
        mPlayerController = DongLingApplication.getPlayerController();
        mPlayerController.addMusicChangedListener(this);
        configLyrics();
        onMusicInfoChanged();
    }

    private String getLyricsFontPath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath()+"/donglingMusic/fonts/donglingfont.ttf";
    }

    private void configLyrics() {
        mLyricsController = new LyricsController(mContainer.getContext());
        int lyricsStyle = ConfigManager.getInstance().getInt("LyricsStyle", 0);
        LyricsViewStyle style = LyricsViewStyle.VerticalNormalStyle;
        if(lyricsStyle != 0){
            style = LyricsViewStyle.HorizontalStyle;
        }
        LyricsViewConfig config =
                new LyricsViewConfig()
                        .setLyricsViewStyle(style)
                        .setLyricsCount(9)
                        .setFocusColor(Color.YELLOW)
                        .setEmptyLyricsView(new DongLingEmptyLyricsView(mContainer.getContext()))
                        .setTextSize(18);
        try{
            config.setTypeface(Typeface.createFromFile(getLyricsFontPath()));
        }catch (Exception e){
            e.printStackTrace();
        }
        mLyricsController.init(config);
        mLyricsController.setOnLocateCenterListener(new ILocateProgressListener() {
            @Override
            public void onLocateCenter(long progress) {
                if(mPlayerController != null){
                    mPlayerController.seekTo((int)progress);
                    if(mPlayerController.isPlaying()){
                        mLyricsController.play(progress);
                    }else{
                        mLyricsController.pause(progress);
                    }
                }
            }
        });
    }

    public void forward(){
        if(mHasLoaded && mPlayerController != null && (mPlayerController.isPlaying() || mPlayerController.isPause())){
            mLyricsController.forward(mPlayerController.getCurTime(),LYRICS_ADJUST_TIME);
        }
    }

    public void backward(){
        if(mHasLoaded && mPlayerController != null && (mPlayerController.isPlaying() || mPlayerController.isPause())){
            mLyricsController.backward(mPlayerController.getCurTime(),LYRICS_ADJUST_TIME);
        }
    }

    private void fetchLyrics(final MusicEntity curMusic){
        mHasLoaded = false;
        new AsyncTask<LyricsResult>().doInBackground(new IDoInBackground<LyricsResult>() {
            @Override
            public LyricsResult run() throws AsyncException {
                LyricsResult result = LyricsResult.fetchLyrics(curMusic);
                if(result == null){
                    return new LyricsResult();
                }
                return result;
            }
        }).doOnSuccess(new IDoOnSuccess<LyricsResult>() {
            @Override
            public void onSuccess(LyricsResult result) {
                startLyrics(result);
                mHasLoaded = true;
            }
        }).doOnFail(new IDoOnFail() {
            @Override
            public void onFail(Throwable e) {
                e.printStackTrace();
                startLyrics(new LyricsResult());
                mHasLoaded = true;
            }
        }).execute();
    }

    private void startLyrics(LyricsResult result) {
        int curTime = 0;
        if(mPlayerController.isPlaying() || mPlayerController.isPause()){
            curTime = mPlayerController.getCurTime();
        }
        mLyricsController.setLyrics(result.getLyrics(),result.getTimeList(),curTime);
        View lyricsView = mLyricsController.getLyricsView();
        mContainer.removeAllViews();
        mContainer.addView(lyricsView,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        if(mPlayerController.isPlaying()){
            mLyricsController.play();
        }else if(curTime > 0 && mPlayerController.isPause()){
            mLyricsController.pause(curTime);
        }
    }

    public void reloadLyrics(){
        onMusicInfoChanged();
    }

    @Override
    public void onMusicInfoChanged() {
        fetchLyrics(mPlayerController.getCurMusic());
    }

    @Override
    public void onPlayInvoke() {
        if(mHasLoaded){
            mLyricsController.play();
        }
    }

    @Override
    public void onPauseInvoke() {
        if(mHasLoaded){
            mLyricsController.pause(mPlayerController.getCurTime());
        }
    }

    @Override
    public void onStopInvoke() {
//        mLyricsController.
    }

    @Override
    public void onMusicOpening() {

    }

    @Override
    public void onMusicBuffering(float percentage) {

    }

    @Override
    public void onMusicProgressChanged(long time) {
        if(mPlayerController.isPlaying()){
            mLyricsController.play(time);
        }else{
            mLyricsController.pause(time);
        }
    }

    @Override
    public void onError(String msg) {

    }

    public void onDestroy(){
        mPlayerController.removeMusicChangedListener(this);
    }
}
