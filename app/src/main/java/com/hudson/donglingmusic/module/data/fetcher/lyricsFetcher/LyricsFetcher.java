package com.hudson.donglingmusic.module.data.fetcher.lyricsFetcher;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.hudson.donglingmusic.common.Utils.MusicUtils.LocalMusicUtils;
import com.hudson.donglingmusic.common.Utils.networkUtils.inner.HttpRequestUtils;
import com.hudson.donglingmusic.entity.LyricsResult;
import com.hudson.donglingmusic.entity.MusicEntity;
import com.hudson.donglingmusic.entity.NetMusicInfo;
import com.hudson.donglingmusic.module.data.fetcher.BaseDataFetcher;

import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Hudson on 2020/3/8.
 */
public class LyricsFetcher extends BaseDataFetcher<LyricsResult> {
    private MusicEntity mTargetMusic;

    public LyricsFetcher(MusicEntity targetMusic) {
        mTargetMusic = targetMusic;
    }

    @Override
    protected LyricsResult fetchDataFromLocal() {
        String lyricsPath = LocalMusicUtils.getMusicLocalLyricsFilePath(mTargetMusic);
        if(!TextUtils.isEmpty(lyricsPath)){
            return LocalLyricsDecoder.decode(lyricsPath);
        }
        return null;
    }

    @Nullable
    @Override
    protected LyricsResult fetchDataFromSever() {
        if(mTargetMusic.isNetMusic()){
            String lrcLink = mTargetMusic.getLyricsLink();
            if(TextUtils.isEmpty(lrcLink)){
                NetMusicInfo result = NetMusicInfo.fetch(mTargetMusic.getSongId());
                lrcLink = result.getSongInfo().getLrcLink();
            }
            Response response;
            try {
                Log.e("hudson","歌词地址"+lrcLink);
                response = HttpRequestUtils.get(lrcLink);
                if(response.isSuccessful()){
                    ResponseBody body = response.body();
                    if(body != null){
                        return LocalLyricsDecoder.readLrcFromInputStream(
                                new InputStreamReader(body.byteStream()));
                        // TODO: 2020/3/8 缓存
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
