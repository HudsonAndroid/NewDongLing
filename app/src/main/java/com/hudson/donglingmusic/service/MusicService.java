package com.hudson.donglingmusic.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.Utils.CommonUtils;
import com.hudson.donglingmusic.Utils.ToastUtils;
import com.hudson.donglingmusic.service.exception.PlayListEmptyException;
import com.hudson.donglingmusic.service.playState.PlayStateHelper;
import com.hudson.donglingplayer.AbsMediaPlayer;
import com.hudson.donglingplayer.VLCPlayer.VlcPlayer;
import com.hudson.donglingplayer.listener.PlayerStateListener;
import com.hudson.musicentitylib.MusicEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hudson on 2019/1/20.
 */
public class MusicService extends Service implements  PlayerStateListener {
    private AbsMediaPlayer mPlayer;
    private final List<MusicEntity> mPlayList;
    private PlayStateHelper mStateHelper;
    private int mCurIndex = 0;

    public MusicService(){
        Context context = CommonUtils.getContext();
        Log.e("hudson","对象为空?"+context);
        mPlayer = new VlcPlayer(context);
        mPlayer.setStateChangeListener(this);
        mPlayList = new ArrayList<>();
        mStateHelper = new PlayStateHelper();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ExposeMethodEntity();
    }

    public void setPlayList(List<MusicEntity> playList){
        mPlayList.clear();
        mPlayList.addAll(playList);
        mStateHelper.setSize(mPlayList.size());
        mCurIndex = 0;
    }

    public void next(){
        try {
            int nextIndex = mStateHelper.getNextIndex(mCurIndex);
            play(nextIndex);
        }catch (PlayListEmptyException e){
            e.printStackTrace();
            ToastUtils.showToast(e.getMessage());
            stop();
        }catch (RuntimeException e){
            e.printStackTrace();
            stop();
        }
    }

    public void pre(){
        try {
            int preIndex = mStateHelper.getPreIndex(mCurIndex);
            play(preIndex);
        }catch (PlayListEmptyException e){
            e.printStackTrace();
            ToastUtils.showToast(e.getMessage());
            stop();
        }catch (RuntimeException e){
            e.printStackTrace();
            stop();
        }
    }

    public void play(){
        mPlayer.play();
    }

    public void pause(){
        mPlayer.pause();
    }

    public void stop(){
        mPlayer.stop();
    }

    public void play(int index){
        int size = mPlayList.size();
        if(size != 0 && index >= 0&& index < size){
            MusicEntity music = mPlayList.get(index);
            mPlayer.reset();
            String path = music.getPath();
            if(!TextUtils.isEmpty(path)){
                mPlayer.init(path);
                mCurIndex = index;
            }else{
                ToastUtils.showToast(R.string.play_source_not_exist);
            }
        }
    }

    @Override
    public void mediaOpening() {

    }

    @Override
    public void mediaStartReady() {
        mPlayer.play();
    }

    @Override
    public void mediaBuffering(float percent) {

    }

    @Override
    public void onProgressPercentChange(float percent) {

    }

    @Override
    public void onProgressTimeChange(long time) {

    }

    @Override
    public void onEnd() {
        next();
    }

    @Override
    public void onError(String msg) {

    }

    private class ExposeMethodEntity extends Binder implements IPlayerController{

        public void setPlayList(List<MusicEntity> playList){
            MusicService.this.setPlayList(playList);
        }

        public void play(){
            MusicService.this.play();
        }

        public void play(int index){
            MusicService.this.play(index);
        }

        public void pause(){
            MusicService.this.pause();
        }

        public void stop(){
            MusicService.this.stop();
        }

        public void next(){
            MusicService.this.next();
        }

        public void pre(){
            MusicService.this.pre();
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
        @Nullable
        public MusicEntity getCurMusic() {
            if(mPlayList.size() != 0){
                return mPlayList.get(mCurIndex);
            }
            return null;
        }

        @Override
        public int getCurProgress() {
            return (int)mPlayer.getTime();
        }
    }
}
