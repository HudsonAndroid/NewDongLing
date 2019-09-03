package com.hudson.donglingmusic.UI.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hudson.cirlcevisiblemusic.CircleVisibleView;
import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.service.IPlayerController;
import com.hudson.donglingmusic.service.MusicService;
import com.hudson.musicentitylib.MusicEntity;
import com.hudson.newlyricsview.lyrics.LyricsController;
import com.hudson.newlyricsview.lyrics.view.config.LyricsViewConfig;
import com.hudson.newlyricsview.lyrics.view.locateProgress.ILocateProgressListener;
import com.hudson.newlyricsview.lyrics.view.style.LyricsViewStyle;

/**
 * 播放页面
 * Created by Hudson on 2019/1/20.
 */
public class PlayPageActivity extends Activity {
    private Visualizer mVisualizer;
    private IPlayerController mPlayerController;
    private ServiceConnectionEntity mConn;
    private CircleVisibleView mVisibleView;
//    private CircleVisibleMusicView mCircleVisibleView;
    private RelativeLayout mLyricsContainer;
    private LyricsController mLyricsController;
    private TextView mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_page);
        mLyricsContainer = findViewById(R.id.rl_lyrics_container);
        mVisibleView = findViewById(R.id.cvv_container);
//        mCircleVisibleView = findViewById(R.id.cvm_music);
        mTitle = findViewById(R.id.tv_title);
        bindService(this);
        initLyrics();
    }

    private void initLyrics() {
        mLyricsController = new LyricsController(this);
        LyricsViewConfig config =
                new LyricsViewConfig()
                        .setLyricsViewStyle(LyricsViewStyle.VerticalNormalStyle)
                        .setLyricsCount(9);
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

    private void bindService(Context context){
        Intent intent = new Intent(context,MusicService.class);
        mConn = new ServiceConnectionEntity();
        context.bindService(intent, mConn,BIND_AUTO_CREATE);
    }

    private class ServiceConnectionEntity implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPlayerController = (IPlayerController) service;
            int audioSessionId = mPlayerController.getAudioSessionId();
            if(audioSessionId != -1){
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
            MusicEntity curMusic = mPlayerController.getCurMusic();
            if(curMusic != null){
                String path = curMusic.getPath().replace(".mp3", ".lrc");
                mLyricsController.decodeLyrics(PlayPageActivity.this,path);
                mLyricsController.initialLyricsView(mPlayerController.getCurProgress());
                View lyricsView = mLyricsController.getLyricsView();
                mLyricsContainer.removeAllViews();
                mLyricsContainer.addView(lyricsView,new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                if(mPlayerController.isPlaying()){
                    mLyricsController.play();
                }
                mTitle.setText(curMusic.getTitle());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mVisualizer!=null){
            mVisualizer.setEnabled(false);
            mVisualizer = null;
        }
        unbindService(mConn);
    }
}
