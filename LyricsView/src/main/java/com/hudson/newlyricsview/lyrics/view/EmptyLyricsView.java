package com.hudson.newlyricsview.lyrics.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.hudson.newlyricsview.R;
import com.hudson.newlyricsview.lyrics.entity.Lyrics;
import com.hudson.newlyricsview.lyrics.schedule.strategy.AbsScheduleWork;
import com.hudson.newlyricsview.lyrics.view.locateProgress.ILocateProgressListener;

import java.util.List;


/**
 * Created by hpz on 2018/12/7.
 */
public class EmptyLyricsView extends TextView implements ILyricsView {
    public EmptyLyricsView(Context context) {
        this(context, null);
    }

    public EmptyLyricsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyLyricsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setText(R.string.lyrics_not_found);
        setGravity(Gravity.CENTER);
    }

    @Override
    public void setLocateCenterListener(ILocateProgressListener listener) {

    }

    @Override
    public void setLyrics(List<Lyrics> lyrics, List<Long> timeList, long startTime) {

    }

    @Nullable
    @Override
    public List<Lyrics> getLyrics() {
        return null;
    }

    @Override
    public void setLyricsTypeface(Typeface typeface) {
        try {
            setTypeface(typeface);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void setLyricsTextSize(float textSize) {
        setTextSize(textSize);
    }

    @Override
    public int setLyricsCount(int count) {
        return 1;
    }

    @Override
    public void setScheduleType(AbsScheduleWork scheduleWork) {

    }

    @Override
    public void setFocusLyricsColor(int color) {
        setTextColor(color);
    }

    @Override
    public void setNormalLyricsColor(int color) {

    }

    @Override
    public void play(long currentProgress) {

    }

    @Override
    public void play() {

    }

    @Override
    public boolean isPlaying() {
        return true;
    }

    @Override
    public Lyrics getCurLyrics() {
        return null;
    }


    @Override
    public void forward(long currentProgress,long timeOffset) {

    }

    @Override
    public void backward(long currentProgress,long timeOffset) {

    }

    @Override
    public void next() {

    }

    @Override
    public void pause(long time) {

    }

    @Override
    public View getView() {
        return this;
    }
}
