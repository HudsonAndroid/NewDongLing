package com.hudson.donglingplayer;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hudson.donglingplayer.exception.PlayerNotInitException;
import com.hudson.donglingplayer.exception.UnSupportOperationException;
import com.hudson.donglingplayer.listener.PlayerStateListener;

/**
 * Created by hudson on 2019/8/29.
 */
public abstract class AbsMediaPlayer {
    private static final int PLAY_STATE_UNDEFINED = -1;//未定义
    private static final int PLAY_STATE_READY = 0;//准备就绪
    private static final int PLAY_STATE_PAUSE = 1;//暂停
    private static final int PLAY_STATE_PLAYING = 2;//播放中
    private int mPlayState = PLAY_STATE_UNDEFINED;
    protected PlayerStateListener mStateListener;

    public void addTimedTextSource(String path, String mimeType) throws UnSupportOperationException{
        throw new UnSupportOperationException();
    }

    public void addTimedTextSource(Context context, Uri uri, String mimeType) throws UnSupportOperationException {
        throw new UnSupportOperationException();
    }

    public void setAudioSessionId(int sessionId) throws UnSupportOperationException {
        throw new UnSupportOperationException("Maybe you can use android native player");
    }

    public int getAudioSessionId() {
        return -1;
    }

    public void setVolume(float leftVolume, float rightVolume) throws UnSupportOperationException {
        throw new UnSupportOperationException();
    }

    public void release(){
        releaseInner();
        Log.e("hudson","被释放了");
        mPlayState = PLAY_STATE_UNDEFINED;
    }

    public void setTime(long time) throws PlayerNotInitException {
        if(mPlayState != PLAY_STATE_UNDEFINED){
            setTimeInner(time);
        }else{
            throw new PlayerNotInitException("setTime()");
        }
    }

    public void init(@NonNull String path){
        mPlayState = PLAY_STATE_READY;
        initInner(path);
    }

    public void play(){
        if(mPlayState == PLAY_STATE_READY || mPlayState == PLAY_STATE_PAUSE){
            playInner();
            mPlayState = PLAY_STATE_PLAYING;
        }else{
            Log.w(getClass().getSimpleName(),"you can't play in this state,so skip");
        }
    }

    public void pause(){
        if(mPlayState == PLAY_STATE_PLAYING){
            pauseInner();
            mPlayState = PLAY_STATE_PAUSE;
        }else{
            Log.w(getClass().getSimpleName(),"you can't pause in this state,so skip");
        }
    }

    public void stop() throws PlayerNotInitException {
        if(mPlayState != PLAY_STATE_UNDEFINED){
            stopInner();
        }
    }

    protected abstract void releaseInner();
    protected abstract void setTimeInner(long time);
    protected abstract void initInner(@NonNull String path);
    protected abstract void playInner();
    protected abstract void pauseInner();
    protected abstract void stopInner();

    public abstract void reset();
    public abstract long getDuration();
    public abstract long getTime();

    public boolean isPlaying(){
        return mPlayState == PLAY_STATE_PLAYING;
    }

    public boolean isPause(){
        return mPlayState == PLAY_STATE_PAUSE;
    }

    /**
     * 设置播放回调接口
     * @param listener callback
     */
    public void setStateChangeListener(@NonNull PlayerStateListener listener) {
        mStateListener = listener;
    }
}
