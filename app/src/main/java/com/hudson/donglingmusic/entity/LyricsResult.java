package com.hudson.donglingmusic.entity;

import com.hudson.donglingmusic.module.data.fetcher.lyricsFetcher.LyricsFetcher;
import com.hudson.newlyricsview.lyrics.entity.Lyrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Hudson on 2020/3/8.
 */
public class LyricsResult {
    private final List<Lyrics> mLyrics = new ArrayList<>();
    private final List<Long> mTimeList = new ArrayList<>();

    public boolean isEmpty(){
        return mLyrics.size() == 0 && mTimeList.size() == 0;
    }

    public List<Lyrics> getLyrics() {
        return mLyrics;
    }

    public void addLyricsItem(Lyrics lyrics){
        mLyrics.add(lyrics);
    }

    public List<Long> getTimeList() {
        return mTimeList;
    }

    public void addTimeItem(long time){
        mTimeList.add(time);
    }

    public LyricsResult sort(){
        Collections.sort(mTimeList);
        Collections.sort(mLyrics);
        return this;
    }

    public static LyricsResult fetchLyrics(MusicEntity target){
        return new LyricsFetcher(target).fetchData();
    }
}
