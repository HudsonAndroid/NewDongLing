package com.hudson.donglingmusic.UI.activity.lyricsMake.maker;

/**
 * Created by Hudson on 2020/3/12.
 */
public interface ILyricsMaker {
    void onNext(long time,String lyrics) throws InvalidTimeLyricsException;

    void onLast();

    boolean save(String path);

    void onReset();

    void modifyLyrics(int position,String lyricsContent);
}
