package com.hudson.donglingmusic.service.playState;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.service.playState.shuffle.ShuffleState;

/**
 * Created by Hudson on 2020/3/13.
 */
public class LoopListState extends BaseState {
    public LoopListState() {
        super(R.drawable.play_mode_looper_list,R.string.play_mode_loop_list);
    }

    @Override
    protected int pre(int curIndex) {
        if(curIndex > 0){
            return curIndex - 1;
        }else{
            return mSize - 1;
        }
    }

    @Override
    protected int next(int curIndex) {
        if(curIndex < mSize - 1){
            return curIndex + 1;
        }else{
            return 0;
        }
    }

    @Override
    protected IState createNextState() {
        return new ShuffleState();
    }
}
