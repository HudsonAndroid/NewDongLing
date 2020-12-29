package com.hudson.donglingplayer.listener;

import com.hudson.donglingplayer.AbsMediaPlayer;

/**
 * 播放器状态变动监听器
 * Created by hudson on 2019/8/29.
 */
public interface PlayerStateListener {
    /**
     * 当媒体正在打开过程中时回调
     * If the player core is VLC,this method will
     * be invoked after you call {@link AbsMediaPlayer#play()},otherwise,
     * if the player core using other core like native core,it will
     * be invoked after you call {@link AbsMediaPlayer#init(String)}.
     */
    void mediaOpening();

    /**
     * You can invoke {@link AbsMediaPlayer#play()} to start after this
     * method invoked.
     */
    void mediaStartReady();

    /**
     * 当媒体缓存时回调
     */
    void mediaBuffering(float percent);

    /**
     * 当播放结束时回调
     */
    void onEnd();

    /**
     * 出错了
     */
    void onError(String msg);
}
