package com.hudson.donglingmusic.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.Utils.ToastUtils;
import com.hudson.donglingmusic.service.exception.PlayListEmptyException;
import com.hudson.donglingmusic.service.playState.PlayStateHelper;
import com.hudson.donglingplayer.IDongLingPlayer;
import com.hudson.donglingplayer.IOnCompletionListener;
import com.hudson.donglingplayer.IOnPreparedListener;
import com.hudson.donglingplayer.SystemPlayer.SystemPlayer;
import com.hudson.musicentitylib.MusicEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hudson on 2019/1/20.
 */
public class MusicService extends Service implements IOnPreparedListener, IOnCompletionListener {
    private IDongLingPlayer mPlayer;
    private final List<MusicEntity> mPlayList;
    private PlayStateHelper mStateHelper;
    private int mCurIndex = 0;
    private IPrepareListener mPrepareListener;

    public MusicService(){
        mPlayer = new SystemPlayer();
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

    public int pause(){
        return mPlayer.pause();
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
                try {
                    mPlayer.setDataSource(path);
                    prepare();
                    mPlayer.setOnPreparedListener(this);
                    mPlayer.setOnCompletionListener(this);
                    mCurIndex = index;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                ToastUtils.showToast(R.string.play_source_not_exist);
            }
        }
    }

    public void setPrepareListener(@NonNull IPrepareListener prepareListener) {
        mPrepareListener = prepareListener;
    }

    private void prepare(){
        if(mPrepareListener != null){
            mPrepareListener.onPrepareStart();
        }
        mPlayer.prepareAsync();
    }

    @Override
    public void onPrepared() {
        if(mPrepareListener != null){
            mPrepareListener.onPrepareCompleted();
        }
        mPlayer.play();
    }

    @Override
    public void onComplete() {
        next();
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

        public int pause(){
            return MusicService.this.pause();
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

        public void setPrepareListener(@NonNull IPrepareListener prepareListener){
            MusicService.this.setPrepareListener(prepareListener);
        }

        @Override
        public int getAudioSessionId() {
            return mPlayer.getAudioSessionId();
        }

        @Override
        public void seekTo(int progress) {
            mPlayer.seekTo(progress);
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
            return mPlayer.getCurProgress();
        }
    }
}
