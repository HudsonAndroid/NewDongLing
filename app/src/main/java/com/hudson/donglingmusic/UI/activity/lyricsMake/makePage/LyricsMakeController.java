package com.hudson.donglingmusic.UI.activity.lyricsMake.makePage;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Hudson on 2020/3/13.
 */
public class LyricsMakeController {
    private ILyricsMakeState mState = new NonLyricsState();
    private RecyclerView mRecyclerView;

    public LyricsMakeController(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    public void setState(ILyricsMakeState state) {
        mState = state;
    }
    
    RecyclerView getRecyclerView(){
        return mRecyclerView;
    }

    public boolean isMaking(){
        return mState instanceof MakingState;
    }

    public void applyLyricsModify(){
        if(isMaking()){
            ((MakingState)mState).applyLyricsModify();
        }
    }

    public void onRestart() {
       mState.onRestart();
    }


    public void onPlayInvoke() {
       mState.onPlayInvoke();
    }


    public void onComplete() {
       mState.onComplete();
    }


    public void onBackward() {
       mState.onBackward();
    }


    public void onForward() {
       mState.onForward();
    }


    public void onPre() {
       mState.onPre();
    }


    public void onNext() {
       mState.onNext();
    }
}
