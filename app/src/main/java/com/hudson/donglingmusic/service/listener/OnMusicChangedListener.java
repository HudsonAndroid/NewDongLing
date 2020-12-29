package com.hudson.donglingmusic.service.listener;

/**
 * Created by Hudson on 2019/11/10.
 */
public interface OnMusicChangedListener {
    void onMusicInfoChanged();
    void onPlayInvoke();
    void onPauseInvoke();
    void onStopInvoke();
    void onMusicOpening();
    void onMusicBuffering(float percentage);
    void onMusicProgressChanged(long time);
    void onError(String msg);
}
