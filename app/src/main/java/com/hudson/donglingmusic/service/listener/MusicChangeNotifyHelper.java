package com.hudson.donglingmusic.service.listener;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hudson on 2019/11/10.
 */
public class MusicChangeNotifyHelper {
    private final List<OnMusicChangedListener> mChangedListeners = new ArrayList<>();
    private float mStashedBufferingPercentage = -1;
    private String mStashedErrorMsg = null;

    public void addListener(@NonNull OnMusicChangedListener listener){
        if(!mChangedListeners.contains(listener)){
            mChangedListeners.add(listener);
            checkStashedInfo(listener);
        }
    }

    private void checkStashedInfo(@NonNull OnMusicChangedListener listener){
        if(mStashedBufferingPercentage > 0){
            listener.onMusicBuffering(mStashedBufferingPercentage);
        }
        if(mStashedErrorMsg != null){
            listener.onError(mStashedErrorMsg);
        }
    }

    public void removeListener(@NonNull OnMusicChangedListener listener){
        mChangedListeners.remove(listener);
    }

    public void notifyMusicProgressChanged(long time){
        for (OnMusicChangedListener changedListener : mChangedListeners) {
            changedListener.onMusicProgressChanged(time);
        }
    }

    public void notifyMusicInfoChanged(){
        for (OnMusicChangedListener changedListener : mChangedListeners) {
            changedListener.onMusicInfoChanged();
        }
    }

    public void notifyPlayInvoke(){
        for (OnMusicChangedListener changedListener : mChangedListeners) {
            changedListener.onPlayInvoke();
        }
    }

    public void notifyPauseInvoke(){
        for (OnMusicChangedListener changedListener : mChangedListeners) {
            changedListener.onPauseInvoke();
        }
    }

    public void notifyStopInvoke(){
        for (OnMusicChangedListener changedListener : mChangedListeners) {
            changedListener.onStopInvoke();
        }
    }

    public void notifyMusicOpening(){
        for (OnMusicChangedListener changedListener : mChangedListeners) {
            changedListener.onMusicOpening();
        }
    }

    public void notifyMusicBuffering(float percentage){
        for (OnMusicChangedListener changedListener : mChangedListeners) {
            changedListener.onMusicBuffering(percentage);
        }
        mStashedBufferingPercentage = percentage;
    }

    public void notifyError(String msg){
        for (OnMusicChangedListener changedListener : mChangedListeners) {
            changedListener.onError(msg);
        }
        mStashedErrorMsg = msg;
    }

}
