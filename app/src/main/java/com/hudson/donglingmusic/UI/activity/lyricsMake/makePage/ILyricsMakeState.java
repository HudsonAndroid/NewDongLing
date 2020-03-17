package com.hudson.donglingmusic.UI.activity.lyricsMake.makePage;

/**
 * Created by Hudson on 2020/3/13.
 */
public interface ILyricsMakeState {
    void onRestart();
    void onPlayInvoke();
    void onComplete();
    void onBackward();
    void onForward();
    void onPre();
    void onNext();
}
