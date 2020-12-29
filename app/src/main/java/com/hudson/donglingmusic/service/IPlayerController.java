package com.hudson.donglingmusic.service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hudson.donglingmusic.entity.MusicEntity;
import com.hudson.donglingmusic.service.listener.OnMusicChangedListener;
import com.hudson.donglingmusic.service.playState.IState;

import java.util.List;

/**
 * Created by Hudson on 2019/1/20.
 */
public interface IPlayerController {
    void setPlayList(@NonNull List<MusicEntity> playList,@NonNull String uniqueTag);

    @NonNull
    List<MusicEntity> getPlayList();

    void play();

    void play(int index);

    void pause();

    void stop();

    void next();

    void pre();

    int getAudioSessionId();

    void seekTo(int progress);

    boolean isPlaying();

    boolean isPause();

    @Nullable
    MusicEntity getCurMusic();

    int getCurTime();

    int switchPlayState();

    void setPlayState(IState playState);

    IState getPlayState();

    void addMusicChangedListener(@NonNull OnMusicChangedListener listener);

    void removeMusicChangedListener(@NonNull OnMusicChangedListener listener);
}
