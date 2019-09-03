package com.hudson.donglingmusic.service;

import android.support.annotation.Nullable;

import com.hudson.musicentitylib.MusicEntity;

import java.util.List;

/**
 * Created by Hudson on 2019/1/20.
 */
public interface IPlayerController {
    void setPlayList(List<MusicEntity> playList);

    void play();

    void play(int index);

    void pause();

    void stop();

    void next();

    void pre();

    int getAudioSessionId();

    void seekTo(int progress);

    boolean isPlaying();

    @Nullable
    MusicEntity getCurMusic();

    int getCurProgress();
}
