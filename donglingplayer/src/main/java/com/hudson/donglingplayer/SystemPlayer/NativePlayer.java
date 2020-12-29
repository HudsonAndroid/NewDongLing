package com.hudson.donglingplayer.SystemPlayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.hudson.donglingplayer.AbsMediaPlayer;

import java.io.IOException;

/**
 * Created by hudson on 2019/8/29.
 */
public class NativePlayer extends AbsMediaPlayer {
    private MediaPlayer mMediaPlayer;

    public NativePlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if(mStateListener != null){
                    mStateListener.onError("native player error,errorType:"+what+" errorCode:"+extra);
                }
                return false;
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(mStateListener != null){
                    mStateListener.onEnd();
                }
            }
        });
    }

    @Override
    protected void releaseInner() {
        mMediaPlayer.release();
    }

    @Override
    public long getDuration() {
        return mMediaPlayer.getDuration();
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    @Override
    protected void setTimeInner(long time) {
        mMediaPlayer.seekTo((int)time);
    }

    @Override
    public long getTime() {
        return mMediaPlayer.getCurrentPosition();
    }

    @Override
    protected void initInner(@NonNull String path) {
        try {
            mMediaPlayer.setDataSource(path);
            if(mStateListener != null){
                mStateListener.mediaOpening();
            }
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if(mStateListener != null){
                        mStateListener.mediaStartReady();
                    }
                }
            });
            mMediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
            if(mStateListener != null){
                mStateListener.onError(e.getMessage());
            }
        }
    }

    @Override
    protected void playInner() {
        mMediaPlayer.start();
    }

    @Override
    protected void pauseInner() {
        mMediaPlayer.pause();
    }

    @Override
    protected void stopInner() {
        mMediaPlayer.stop();
    }

    @Override
    public void reset() {
        mMediaPlayer.reset();
    }

    @Override
    public void addTimedTextSource(String path, String mimeType) {
        try {
            mMediaPlayer.addTimedTextSource(path,mimeType);
        } catch (IOException e) {
            e.printStackTrace();
            if(mStateListener != null){
                mStateListener.onError(e.getMessage());
            }
        }
    }

    @Override
    public void addTimedTextSource(Context context, Uri uri, String mimeType) {
        try {
            mMediaPlayer.addTimedTextSource(context,uri,mimeType);
        } catch (IOException e) {
            e.printStackTrace();
            if(mStateListener != null){
                mStateListener.onError(e.getMessage());
            }
        }
    }

    @Override
    public void setAudioSessionId(int sessionId) {
        mMediaPlayer.setAudioSessionId(sessionId);
    }

    @Override
    public int getAudioSessionId() {
        return mMediaPlayer.getAudioSessionId();
    }

    @Override
    public void setVolume(float leftVolume, float rightVolume) {
        mMediaPlayer.setVolume(leftVolume,rightVolume);
    }
}
