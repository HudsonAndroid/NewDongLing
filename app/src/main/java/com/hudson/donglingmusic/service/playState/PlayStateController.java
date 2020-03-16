package com.hudson.donglingmusic.service.playState;

import com.hudson.donglingmusic.service.exception.PlayListEmptyException;

/**
 * Created by Hudson on 2019/1/12.
 */
public class PlayStateController {
    private IState mPlayState;

    public void setCurIndex(int curIndex){
        mPlayState.setCurPlayIndex(curIndex);
    }

    public PlayStateController(){
        mPlayState = new LoopListState();
    }

    public int getNextIndex(int curIndex) throws PlayListEmptyException,IndexOutOfBoundsException{
        return mPlayState.getNext(curIndex);
    }

    public int getPreIndex(int curIndex) throws PlayListEmptyException,IndexOutOfBoundsException{
        return mPlayState.getPre(curIndex);
    }

    public int switchState(){
        IState next = mPlayState.getNextState();
        if(next != null){
            mPlayState = next;
        }
        return mPlayState.getDrawableId();
    }

    public void setPlayState(IState playState) {
        mPlayState = playState;
    }

    public void setSize(int size) {
        mPlayState.setSize(size);
    }

    public IState getPlayState(){
        return mPlayState;
    }

}
