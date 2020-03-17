package com.hudson.donglingmusic.UI.activity.lyricsMake.maker;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.global.DongLingApplication;

/**
 * Created by Hudson on 2020/3/12.
 */
public class InvalidTimeLyricsException extends IllegalStateException {
    public InvalidTimeLyricsException() {
        super(DongLingApplication.getGlobalContext()
                .getString(R.string.common_lyrics_time_invalid));
    }
}
