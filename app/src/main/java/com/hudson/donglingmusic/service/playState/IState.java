package com.hudson.donglingmusic.service.playState;

/**
 * Created by Hudson on 2020/3/13.
 */
public interface IState {
    void setSize(int size);
    int getPre(int curIndex);
    int getNext(int curIndex);
    int getStrId();
    int getDrawableId();
    IState getNextState();
    void setCurPlayIndex(int curIndex);
}
