package com.hudson.donglingmusic.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hudson.donglingmusic.entity.MusicEntity;
import com.hudson.donglingmusic.service.listener.OnMusicChangedListener;
import com.hudson.donglingmusic.service.notify.NotifyHelper;
import com.hudson.donglingmusic.service.playState.IState;
import com.hudson.donglingmusic.service.playerWrapper.MusicPlayer;

import java.util.List;

/**
 * Created by Hudson on 2019/1/20.
 */
public class MusicService extends Service {
    private MusicPlayer mPlayer;
    private NotifyHelper mNotifyHelper;

    public MusicService() {
        mPlayer = new MusicPlayer();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotifyHelper = new NotifyHelper(this);
        mNotifyHelper.startForeground();
        mPlayer.addMusicChangedListener(mNotifyHelper);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNotifyHelper.onDestroy();
        mPlayer.removeMusicChangedListener(mNotifyHelper);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ExposeMethodEntity();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    private class ExposeMethodEntity extends Binder implements IPlayerController{

        public void setPlayList(@NonNull List<MusicEntity> playList,@NonNull String uniqueTag){
            mPlayer.setPlayList(playList,uniqueTag);
        }

        @NonNull
        @Override
        public List<MusicEntity> getPlayList() {
            return mPlayer.getPlayList();
        }

        public void play(){
            mPlayer.play();
        }

        public void play(int index){
            mPlayer.play(index);
        }

        public void pause(){
            mPlayer.pause();
        }

        public void stop(){
            mPlayer.stop();
        }

        public void next(){
            mPlayer.next();
        }

        public void pre(){
            mPlayer.pre();
        }

        @Override
        public int getAudioSessionId() {
            return mPlayer.getAudioSessionId();
        }

        @Override
        public void seekTo(int progress) {
            mPlayer.setTime(progress);
        }

        @Override
        public boolean isPlaying() {
            return mPlayer.isPlaying();
        }

        @Override
        public boolean isPause(){
            return mPlayer.isPause();
        }

        @Override
        @Nullable
        public MusicEntity getCurMusic() {
            return mPlayer.getCurMusic();
        }

        @Override
        public int getCurTime() {
            return (int)mPlayer.getTime();
        }

        @Override
        public void addMusicChangedListener(@NonNull OnMusicChangedListener listener){
            mPlayer.addMusicChangedListener(listener);
        }

        @Override
        public void removeMusicChangedListener(@NonNull OnMusicChangedListener listener){
            mPlayer.removeMusicChangedListener(listener);
        }

        @Override
        public void setPlayState(IState playState){
            mPlayer.setPlayState(playState);
        }

        @Override
        public int switchPlayState(){
            return mPlayer.switchPlayState();
        }

        @Override
        public IState getPlayState(){
            return mPlayer.getPlayState();
        }
    }


}
