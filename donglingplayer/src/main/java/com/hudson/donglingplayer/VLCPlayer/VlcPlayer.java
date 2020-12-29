package com.hudson.donglingplayer.VLCPlayer;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.hudson.donglingplayer.AbsMediaPlayer;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.util.ArrayList;

/**
 * Created by hudson on 2019/8/29.
 */
public class VlcPlayer extends AbsMediaPlayer {
    private LibVLC mLibVLC;
    private MediaPlayer mMediaPlayer;

    public VlcPlayer(Context context) {
        ArrayList<String> options = new ArrayList<>();
//        options.add("--aout=opensles");
//        options.add("--audio-time-stretch"); // time stretching
//        options.add("-vvv"); // verbosity
        mLibVLC = new LibVLC(context, options);
        mMediaPlayer = new MediaPlayer(mLibVLC);
        mMediaPlayer.setEventListener(new MediaPlayer.EventListener() {
            @Override
            public void onEvent(MediaPlayer.Event event) {
                switch (event.type){
                    case MediaPlayer.Event.Opening:
                        if(mStateListener != null){
                            mStateListener.mediaOpening();
                        }
                        break;
                    case MediaPlayer.Event.Buffering:
                        if(mStateListener != null){
                            mStateListener.mediaBuffering(event.getBuffering());
                        }
                        break;
                    case MediaPlayer.Event.EncounteredError://出错了！
                        if(mStateListener != null){
                            mStateListener.onError("vlc player error");
                        }
                        break;
//                    case MediaPlayer.Event.SeekableChanged:
//
//                        break;
//                    case MediaPlayer.Event.PausableChanged:
//
//                        break;
//                    case MediaPlayer.Event.MediaChanged:
//
//                        break;
                    case MediaPlayer.Event.PositionChanged:
//                        if(mStateListener != null){
//                            mStateListener.onProgressPercentChange(event.getPositionChanged());
//                        }
                        break;
                    case MediaPlayer.Event.TimeChanged:
//                        if(mStateListener != null){
//                            mStateListener.onProgressTimeChange(event.getTimeChanged());
//                        }
                        break;
                    case MediaPlayer.Event.EndReached:
                        if(mStateListener != null){
                            mStateListener.onEnd();
                        }
                        break;
                    default:
                        break;
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
        return mMediaPlayer.getLength();
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    @Override
    protected void setTimeInner(long time) {
        mMediaPlayer.setTime(time);
    }

    @Override
    public long getTime() {
        return mMediaPlayer.getTime();
    }

    @Override
    protected void initInner(@NonNull String path) {
        if(mMediaPlayer.isPlaying()){
            stop();
            release();
        }
        mMediaPlayer.setMedia(new Media(mLibVLC, path));
        if(mStateListener != null){
            mStateListener.mediaStartReady();
        }
    }

    @Override
    protected void playInner() {
        mMediaPlayer.play();
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
    public void reset() {}

    @Override
    public void setVolume(float leftVolume, float rightVolume) {
        mMediaPlayer.setVolume((int)((leftVolume + rightVolume) * 100/2));
    }

    @Override
    public void addTimedTextSource(Context context, Uri uri, String mimeType) {
        mMediaPlayer.addSlave(Media.Slave.Type.Subtitle, uri, false);
    }

    @Override
    public void addTimedTextSource(String path, String mimeType) {
        mMediaPlayer.addSlave(Media.Slave.Type.Subtitle, path, false);
    }
}
