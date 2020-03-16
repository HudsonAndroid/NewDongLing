package com.hudson.donglingmusic.service.playState;

import android.util.Log;

import com.hudson.donglingmusic.service.exception.PlayListEmptyException;

/**
 * Created by Hudson on 2020/3/13.
 */
public abstract class BaseState implements IState {
    private int mDrawableId;
    private IState mNext;
    private int mStrId;
    protected int mSize;
    private int mCurPlayIndex = -1;

    public BaseState(int drawableId, int strId) {
        mDrawableId = drawableId;
        mStrId = strId;
    }

    @Override
    public void setSize(int size) {
        mSize = size;
    }

    @Override
    public final int getPre(int curIndex) {
        checkValid(curIndex);
        return pre(curIndex);
    }

    @Override
    public final int getNext(int curIndex) {
        checkValid(curIndex);
        return next(curIndex);
    }

    protected abstract int pre(int curIndex);
    protected abstract int next(int curIndex);

    private void checkValid(int curIndex) throws PlayListEmptyException,IndexOutOfBoundsException{
        if(mSize <= 0){
            throw new PlayListEmptyException();
        }
        if(curIndex < 0){
            throw new IndexOutOfBoundsException("the index is invalid");
        }
    }

    @Override
    public int getDrawableId() {
        return mDrawableId;
    }

    @Override
    public int getStrId() {
        return mStrId;
    }

    @Override
    public void setCurPlayIndex(int curIndex) {
        mCurPlayIndex = curIndex;
    }

    @Override
    public final IState getNextState() {
        if(mNext == null){
            mNext = createNextState();
        }
        // 把当前的一些信息，传递给下一个
        mNext.setSize(mSize);
        mNext.setCurPlayIndex(mCurPlayIndex);
        return mNext;
    }

    protected abstract IState createNextState();
}
