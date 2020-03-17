package com.hudson.donglingmusic.UI.activity.lyricsMake.maker;

import com.hudson.donglingmusic.common.Utils.FileUtils;
import com.hudson.donglingmusic.common.Utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hudson on 2020/3/12.
 */
public class LyricsMaker implements ILyricsMaker {
    private final List<String> mLyrics = new ArrayList<>();
    private final List<Long> mTimes = new ArrayList<>();

    @Override
    public void onNext(long time, String lyrics) throws InvalidTimeLyricsException {
        if(time > getLastTime()){
            mLyrics.add(TimeUtils.transformTime(time)+lyrics);
            mTimes.add(time);
        }else{
            throw new InvalidTimeLyricsException();
        }
    }

    private long getLastTime(){
        if(mTimes.size() > 0){
            return mTimes.get(mTimes.size()-1);
        }
        return -1;
    }

    @Override
    public void onLast() {
        mLyrics.remove(mLyrics.size() - 1);
        mTimes.remove(mTimes.size()-1);
    }

    @Override
    public boolean save(String path) {
        if(mLyrics.size() > 0){
            return FileUtils.saveStrList(mLyrics, path);
        }else{
            return false;
        }
    }

    @Override
    public void onReset() {
        mLyrics.clear();
        mTimes.clear();
    }

    @Override
    public void modifyLyrics(int position, String lyricsContent) {
        if(position >= 0 && position < mLyrics.size()){
            mLyrics.set(position,TimeUtils.transformTime(mTimes.get(position))+lyricsContent);
        }
    }
}
