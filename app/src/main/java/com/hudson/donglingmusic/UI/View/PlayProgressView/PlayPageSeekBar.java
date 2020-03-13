package com.hudson.donglingmusic.UI.View.PlayProgressView;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.common.Utils.TimeUtils;
import com.hudson.donglingmusic.service.IPlayerController;
import com.hudson.donglingmusic.service.musicController.MusicController;

/**
 * Created by Hudson on 2020/3/7.
 */
public class PlayPageSeekBar extends ConstraintLayout implements IProgressView {
    private static final int MAX_PROGRESS = 10000;
    private SeekBar mSeekBar;
    private TextView mLeftTime,mRightTime;
    private long mMusicDuration;

    public PlayPageSeekBar(Context context) {
        this(context, null);
    }

    public PlayPageSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayPageSeekBar(final Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.progress_view_play_page_seekbar,this);
        mSeekBar = (SeekBar) this.findViewById(R.id.sb_progress);
        mLeftTime = (TextView) this.findViewById(R.id.tv_left);
        mRightTime = (TextView) this.findViewById(R.id.tv_right);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mLeftTime.setText(TimeUtils.toTime((long) (progress * 1.0f / MAX_PROGRESS * mMusicDuration)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                IPlayerController controller = MusicController.getController();
                if(controller != null){
                    controller.seekTo((int) (seekBar.getProgress() * 1.0f / MAX_PROGRESS * mMusicDuration));
                }
            }
        });
    }

    @Override
    public void setMusicDuration(long musicDuration) {
        mMusicDuration = musicDuration;
        mRightTime.setText(TimeUtils.toTime(mMusicDuration));
        mSeekBar.setMax(MAX_PROGRESS);
    }

    @Override
    public void setCurProgress(long current) {
        mSeekBar.setProgress((int) (current * MAX_PROGRESS / mMusicDuration));
    }

    @Override
    public void setBufferingPercentage(float percentage) {
        mSeekBar.setSecondaryProgress((int) (percentage * MAX_PROGRESS));
    }
}
