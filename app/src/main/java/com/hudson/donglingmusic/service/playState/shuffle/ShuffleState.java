package com.hudson.donglingmusic.service.playState.shuffle;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.service.playState.BaseState;
import com.hudson.donglingmusic.service.playState.IState;
import com.hudson.donglingmusic.service.playState.LoopOneState;

/**
 * Created by Hudson on 2020/3/13.
 */
public class ShuffleState extends BaseState {
    private IShuffleGenerator mShuffleGenerator;

    public ShuffleState() {
        super(R.drawable.play_mode_shuffle,R.string.play_mode_shuffle);
        mShuffleGenerator = new ListShuffleGenerator();
    }

    @Override
    public void setCurPlayIndex(int curIndex) {
        super.setCurPlayIndex(curIndex);
        mShuffleGenerator.setCurPlayIndex(curIndex);
    }

    @Override
    public void setSize(int size) {
        super.setSize(size);
        mShuffleGenerator.setSize(size);
    }

    @Override
    protected int pre(int curIndex) {
        return mShuffleGenerator.generateShuffleIndex();
    }

    @Override
    protected int next(int curIndex) {
        return mShuffleGenerator.generateShuffleIndex();
    }

    @Override
    protected IState createNextState() {
        return new LoopOneState();
    }
}
