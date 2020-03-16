package com.hudson.donglingmusic.service.playState.shuffle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 思路：
 *  使用List<Integer>列表作为音乐对应列表，
 *  产生随机数依赖random方法，每次产生一个
 *  随机数，从集合中移除对应的Integer对象;
 *  如果集合为空，重置集合.
 * Created by Hudson on 2020/3/9.
 */
public class ListShuffleGenerator implements IShuffleGenerator {
    private int mSize;
    private final List<Integer> mShuffleList = new ArrayList<>();

    @Override
    public void setSize(int size){
        mSize = size;
        if(size > 0){
            resetShuffleList(size);
        }
    }

    @Override
    public void setCurPlayIndex(int playIndex) {
        mShuffleList.remove(Integer.valueOf(playIndex));
    }

    private void resetShuffleList(int size) {
        mShuffleList.clear();
        for (int i = 0; i < size; i++) {
            mShuffleList.add(i);
        }
    }

    @Override
    public Integer generateShuffleIndex() {
        Random random = new Random();
        int result = random.nextInt(mShuffleList.size());
        Integer shuffleIndex = mShuffleList.get(result);
        mShuffleList.remove(shuffleIndex);
        checkListEmpty(shuffleIndex);
        return shuffleIndex;
    }

    private void checkListEmpty(int lastShuffleIndex){
        if(mShuffleList.size() == 0){
            //最后一次的不能作为产生的可能性之一
            resetShuffleList(mSize);
            setCurPlayIndex(lastShuffleIndex);
        }
    }
}
