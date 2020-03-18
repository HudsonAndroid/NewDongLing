package com.hudson.donglingmusic.UI.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearSmoothScroller;

/**
 * Created by Hudson on 2020/3/18.
 */
public class TopLinearSmoothScroller extends LinearSmoothScroller {

    public TopLinearSmoothScroller(Context context) {
        super(context);
    }

    @Override
    public int getVerticalSnapPreference() {
        return SNAP_TO_START;
    }
}
