package com.hudson.donglingmusic.service.playState;

import com.hudson.donglingmusic.R;

/**
 * Created by Hudson on 2020/3/13.
 */
public class LoopOneState extends BaseState {

    public LoopOneState() {
        super(R.drawable.play_mode_looper_one,R.string.play_mode_loop_one);
    }

    @Override
    protected int pre(int curIndex) {
        return curIndex;
    }

    @Override
    protected int next(int curIndex) {
        return curIndex;
    }

    @Override
    protected IState createNextState() {
        return new LoopListState();
    }
}
