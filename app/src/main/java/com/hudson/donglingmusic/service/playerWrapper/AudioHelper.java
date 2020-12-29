package com.hudson.donglingmusic.service.playerWrapper;

import android.content.Context;
import android.media.AudioManager;

import com.hudson.donglingmusic.global.DongLingApplication;

/**
 * Created by Hudson on 2020/3/18.
 */
public class AudioHelper {
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mListener;
    private OnAudioChangeListener mChangeListener;

    public AudioHelper() {
        mAudioManager = (AudioManager) DongLingApplication.getGlobalContext().getSystemService(Context.AUDIO_SERVICE);
    }

    boolean canPlay(){
        if(mListener == null) {
            mListener = new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    switch (focusChange) {
                        case AudioManager.AUDIOFOCUS_GAIN:
                        case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                        case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
                            if(mChangeListener != null){
                                mChangeListener.resumePlay();
                            }
                            break;

                        case AudioManager.AUDIOFOCUS_LOSS:
                        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                            if(mChangeListener != null){
                                mChangeListener.needPause();
                            }
                            break;
                        default:
                            break;
                    }
                }
            };
        }
        return mAudioManager.requestAudioFocus(mListener,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

    public void onDestroy(){
        mAudioManager.abandonAudioFocus(mListener);
    }

    public void setOnAudioChangeListener(OnAudioChangeListener changeListener) {
        mChangeListener = changeListener;
    }

    public interface OnAudioChangeListener{
        void resumePlay();
        void needPause();
    }
}
