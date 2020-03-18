package com.hudson.donglingmusic.UI.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Hudson on 2020/3/18.
 */
public class TopLinearLayoutManager extends LinearLayoutManager {

    public TopLinearLayoutManager(Context context) {
        super(context);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView view, RecyclerView.State state, int position) {
        TopLinearSmoothScroller scroller = new TopLinearSmoothScroller(view.getContext());
        scroller.setTargetPosition(position);
        startSmoothScroll(scroller);
    }
}
