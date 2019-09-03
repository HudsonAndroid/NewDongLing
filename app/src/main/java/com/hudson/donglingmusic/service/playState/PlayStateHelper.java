package com.hudson.donglingmusic.service.playState;

import com.hudson.donglingmusic.service.exception.PlayListEmptyException;

import java.util.Random;

/**
 * Created by Hudson on 2019/1/12.
 */
public class PlayStateHelper {
    public static final int STATE_SEQUENTIAL = 1;
    public static final int STATE_SHUFFLE = 2;
    public static final int STATE_ONE_LOOP = 3;
    private boolean[] mUsedFlags;
    private int mUsefulCount;
    private int mPlayState = STATE_SEQUENTIAL;
    private int mSize;

    public int getNextIndex(int curIndex) throws PlayListEmptyException,IndexOutOfBoundsException{
        checkValid(curIndex);
        return getNext(curIndex);
    }

    private void checkValid(int curIndex) throws PlayListEmptyException,IndexOutOfBoundsException{
        if(mSize <= 0 || mUsedFlags == null){
            throw new PlayListEmptyException();
        }
        if(curIndex < 0){
            throw new IndexOutOfBoundsException("the index is invalid");
        }
        if(!mUsedFlags[curIndex]){//如果未被使用，将其置为已使用状态
            mUsedFlags[curIndex] = true;
            mUsefulCount --;
        }
    }

    public int getPreIndex(int curIndex) throws PlayListEmptyException,IndexOutOfBoundsException{
        checkValid(curIndex);
        return getPre(curIndex);
    }

    /**
     * 检查列表是否都被使用了一遍
     * @param newIndex 新产生的index
     */
    private void checkUseState(int newIndex) {
        if(mUsefulCount <= 0){//都被使用过了一遍,重置
            for (int i = 0; i < mUsedFlags.length; i++) {
                if(i != newIndex){
                    mUsedFlags[i] = false;
                }
            }
        }
    }

    private int getPre(int curIndex) {
        int newIndex;
        switch (mPlayState){
            case STATE_SHUFFLE:
                newIndex = getShuffleIndex();
                break;
            case STATE_ONE_LOOP:
                newIndex = curIndex;
                break;
            case STATE_SEQUENTIAL:
            default:
                if(curIndex > 0){
                    newIndex = curIndex - 1;
                }else{
                    newIndex = mSize - 1;
                }
                break;
        }
        checkUseState(newIndex);
        return newIndex;
    }

    private int getNext(int curIndex){
        int newIndex;
        switch (mPlayState){
            case STATE_SHUFFLE:
                newIndex = getShuffleIndex();
                break;
            case STATE_ONE_LOOP:
                newIndex = curIndex;
                break;
            case STATE_SEQUENTIAL:
            default:
                if(curIndex < mSize - 1){
                    newIndex = curIndex + 1;
                }else{
                    newIndex = 0;
                }
                break;
        }
        checkUseState(newIndex);
        return newIndex;
    }

    private int getShuffleIndex(){
        Random random = new Random();
        int result = random.nextInt(mSize);
        if(mUsedFlags[result]){//这个已经被使用过了
            return getShuffleIndex();
        }else{
            mUsedFlags[result] = true;
            return result;
        }
    }

    public void setPlayState(int playState) {
        mPlayState = playState;
    }

    public void setSize(int size) {
        mSize = size;
        mUsefulCount = size;
        mUsedFlags = new boolean[size];
    }

    public int getPlayState() {
        return mPlayState;
    }

    public int getSize() {
        return mSize;
    }
}
