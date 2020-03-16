package com.hudson.donglingmusic.service.playState.shuffle;

/**
 * 随机播放产生器
 * Created by Hudson on 2020/3/9.
 */
public interface IShuffleGenerator {
    void setSize(int size);
    void setCurPlayIndex(int playIndex);
    Integer generateShuffleIndex();
}
