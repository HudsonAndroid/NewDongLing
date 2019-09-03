package com.hudson.donglingmusic.service;

import android.support.annotation.NonNull;
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

    int pause();

    void stop();

    void next();

    void pre();

    void setPrepareListener(@NonNull IPrepareListener prepareListener);

    int getAudioSessionId();

    void seekTo(int progress);

    boolean isPlaying();

    @Nullable
    MusicEntity getCurMusic();

    int getCurProgress();
}
