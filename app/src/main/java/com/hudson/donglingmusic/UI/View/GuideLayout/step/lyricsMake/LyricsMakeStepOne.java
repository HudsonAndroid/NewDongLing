package com.hudson.donglingmusic.UI.View.GuideLayout.step.lyricsMake;

import android.view.View;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.View.GuideLayout.layout.GuideLayout;
import com.hudson.donglingmusic.UI.View.GuideLayout.step.GuideStep;

/**
 * Created by Hudson on 2020/3/11.
 */
public class LyricsMakeStepOne extends GuideStep {

    public LyricsMakeStepOne() {
        super(R.layout.guide_lyrics_make_step_one, R.id.tv_next);
    }

    @Override
    public void showStep(final GuideLayout parent) {
        super.showStep(parent);
        parent.findViewById(R.id.tv_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.hide(true);
            }
        });
    }
}
