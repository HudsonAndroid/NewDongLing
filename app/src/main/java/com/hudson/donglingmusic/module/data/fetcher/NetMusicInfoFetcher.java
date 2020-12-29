package com.hudson.donglingmusic.module.data.fetcher;

import android.support.annotation.NonNull;

import com.hudson.donglingmusic.entity.NetMusicInfo;
import com.hudson.donglingmusic.module.data.requestParam.IRequestParam;
import com.hudson.donglingmusic.module.data.requestParam.NetMusicInfoRequest;

/**
 * Created by Hudson on 2020/3/5.
 */
public class NetMusicInfoFetcher extends CommonDataFetcher<NetMusicInfo> {
    private int mSongId;

    public NetMusicInfoFetcher(int songId) {
        mSongId = songId;
    }

    @NonNull
    @Override
    protected IRequestParam getRequestParam() {
        return new NetMusicInfoRequest(mSongId);
    }

    @NonNull
    @Override
    protected String getUniqueTag() {
        return String.valueOf(mSongId);
    }
}
