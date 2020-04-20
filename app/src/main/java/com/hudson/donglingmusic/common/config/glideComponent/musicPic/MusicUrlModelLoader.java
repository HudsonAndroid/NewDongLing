package com.hudson.donglingmusic.common.config.glideComponent.musicPic;

import android.content.Context;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.hudson.donglingmusic.common.Utils.networkUtils.inner.HttpRequestUtils;
import com.hudson.donglingmusic.entity.MusicEntity;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Created by Hudson on 2020/4/20.
 */
public class MusicUrlModelLoader implements ModelLoader<MusicEntity, InputStream> {

    private OkHttpClient okHttpClient;

    public static class Factory implements ModelLoaderFactory<MusicEntity, InputStream> {

        @Override
        public ModelLoader<MusicEntity, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new MusicUrlModelLoader(HttpRequestUtils.getClient());
        }

        @Override
        public void teardown() {
        }
    }

    MusicUrlModelLoader(OkHttpClient client) {
        this.okHttpClient = client;
    }

    @Override
    public DataFetcher<InputStream> getResourceFetcher(MusicEntity model, int width, int height) {
        return new MusicUrlFetcher(okHttpClient, model);
    }
}
