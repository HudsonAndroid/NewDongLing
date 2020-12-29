package com.hudson.donglingmusic.module.data.requestParam;

import com.hudson.donglingmusic.common.Utils.aesUtils.AESUtils;

/**
 * Created by Hudson on 2020/3/5.
 */
public class NetMusicInfoRequest extends BaseRequest {
    private int mSongId;

    public NetMusicInfoRequest(int songId) {
        mSongId = songId;
    }

    @Override
    protected String getMethod() {
        return "baidu.ting.song.getInfos";
    }

    @Override
    protected String getExtendParam() {
        String before = "songid="+ mSongId + "&ts="
                + System.currentTimeMillis();
        return "&" + before + "&e=" + AESUtils.encrpty(before);
    }
}
