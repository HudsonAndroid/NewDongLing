package com.hudson.donglingmusic.common.config.glideComponent.musicPic;

import android.graphics.Bitmap;
import android.util.Log;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.model.GlideUrl;
import com.hudson.donglingmusic.common.Utils.bitmapUtils.BitmapUtils;
import com.hudson.donglingmusic.common.config.glideComponent.OkHttpUrlFetcher;
import com.hudson.donglingmusic.entity.MusicEntity;
import com.hudson.donglingmusic.entity.NetMusicInfo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Created by Hudson on 2020/4/20.
 */
public class MusicUrlFetcher extends OkHttpUrlFetcher {
    private MusicEntity mMusicEntity;

    public MusicUrlFetcher(OkHttpClient client,MusicEntity entity){
        super(client);
        mMusicEntity = entity;
    }

    @Override
    public InputStream loadData(Priority priority) throws Exception {
        if(mMusicEntity.isNetMusic()){
            NetMusicInfo musicInfo = NetMusicInfo.fetch(mMusicEntity.getSongId());
            url = new GlideUrl(musicInfo.getSongInfo().getPicBig());
            return super.loadData(priority);
        }else{
            Bitmap bitmap = BitmapUtils.getArtwork(mMusicEntity.getTitle(), mMusicEntity.getAlbumId());
            if(bitmap != null){
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                return new ByteArrayInputStream(baos .toByteArray());
            }
        }
        Log.e("hudson","返回空了");
        throw new RuntimeException("the pic not found");
//        return null;
    }

    @Override
    public String getId() {
        return mMusicEntity.getId() + mMusicEntity.getTitle() + mMusicEntity.getAlbumId();
    }
}
