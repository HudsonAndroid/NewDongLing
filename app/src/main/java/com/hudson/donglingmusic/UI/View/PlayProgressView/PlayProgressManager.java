package com.hudson.donglingmusic.UI.View.PlayProgressView;

import android.support.annotation.NonNull;

import com.hudson.donglingmusic.entity.MusicEntity;
import com.hudson.donglingmusic.service.IPlayerController;
import com.hudson.donglingmusic.service.listener.OnMusicChangedListener;
import com.hudson.donglingmusic.service.musicController.MusicController;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 歌曲播放进度刷新管理器
 * Created by Hudson on 2020/3/7.
 */
public class PlayProgressManager implements OnMusicChangedListener {
    private Timer mTimer;
    private TimerTask mTimerTask;
    private final List<IProgressView> mProgressViews = new ArrayList<>();
    private IPlayerController mPlayerController;
    private static PlayProgressManager sProgressManager;
    private float mStashedBufferingProgress = 0;

    private PlayProgressManager(){
        mPlayerController = MusicController.getController();
    }

    public static PlayProgressManager getInstance(){
        if(sProgressManager == null){
            synchronized (PlayProgressManager.class){
                if(sProgressManager == null){
                    sProgressManager = new PlayProgressManager();
                }
            }
        }
        return sProgressManager;
    }

    private void startTimerTask(){
        if(mTimer == null || mTimerTask == null){
            mTimer = new Timer();
            mPlayerController.addMusicChangedListener(this);
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    if(mPlayerController != null){
                        if(mPlayerController.isPlaying()){
                            updateProgressView(mPlayerController.getCurTime());
                        }
                    }
                }
            };
            mTimer.schedule(mTimerTask, 0, 300);// 每隔300毫秒执行一次
        }
    }

    private void updateProgressView(long curPosition){
        for (IProgressView progressView : mProgressViews) {
            progressView.setCurProgress(curPosition);
        }
    }

    private void stopTimerTask(){
        if(mTimer != null && mTimerTask != null){
            mTimer.cancel();
            mTimerTask.cancel();
            mTimer = null;
            mTimerTask = null;
            mPlayerController.removeMusicChangedListener(this);
        }
    }


    public void addProgressView(@NonNull IProgressView progressView){
        if(!mProgressViews.contains(progressView)){
            MusicEntity curMusic = mPlayerController.getCurMusic();
            if(curMusic != null){
                progressView.setMusicDuration(curMusic.getDuration());
                progressView.setCurProgress(mPlayerController.getCurTime());
                if(mStashedBufferingProgress > 0){
                    progressView.setBufferingPercentage(mStashedBufferingProgress);
                }
            }
            mProgressViews.add(progressView);
            startTimerTask();
        }
    }

    public void removeProgressView(@NonNull IProgressView progressView){
        mProgressViews.remove(progressView);
        if(mProgressViews.size() == 0){
            stopTimerTask();
        }
    }

    @Override
    public void onMusicInfoChanged() {
        mStashedBufferingProgress = 0;
        MusicEntity curMusic = mPlayerController.getCurMusic();
        if(curMusic != null){
            for (IProgressView progressView : mProgressViews) {
                progressView.setMusicDuration(curMusic.getDuration());
            }
        }
    }

    @Override
    public void onPlayInvoke() {}

    @Override
    public void onPauseInvoke() {}

    @Override
    public void onStopInvoke() {}

    @Override
    public void onMusicOpening() {}

    @Override
    public void onMusicBuffering(float percentage) {
        for (IProgressView progressView : mProgressViews) {
            progressView.setBufferingPercentage(percentage);
        }
        mStashedBufferingProgress = percentage;
    }

    @Override
    public void onMusicProgressChanged(long time) {
        for (IProgressView progressView : mProgressViews) {
            progressView.setCurProgress(time);
        }
    }

    @Override
    public void onError(String msg) {
        for (IProgressView progressView : mProgressViews) {
            progressView.setCurProgress(0);
        }
    }
}
