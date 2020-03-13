package com.hudson.donglingmusic.UI.View.PlayProgressView;

/**
 * Created by Hudson on 2020/3/7.
 */
public interface IProgressView {
    void setMusicDuration(long musicDuration);
    void setCurProgress(long current);
    void setBufferingPercentage(float percentage);
}
