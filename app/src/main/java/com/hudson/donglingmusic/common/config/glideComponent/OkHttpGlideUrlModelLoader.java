package com.hudson.donglingmusic.common.config.glideComponent;

import android.content.Context;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.hudson.donglingmusic.common.Utils.networkUtils.inner.HttpRequestUtils;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Created by Hudson on 2020/4/20.
 */
public class OkHttpGlideUrlModelLoader implements ModelLoader<GlideUrl, InputStream> {
    private OkHttpClient okHttpClient;

    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {

        @Override
        public ModelLoader<GlideUrl, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new OkHttpGlideUrlModelLoader(HttpRequestUtils.getClient());
        }

        @Override
        public void teardown() {
        }
    }

    OkHttpGlideUrlModelLoader(OkHttpClient client) {
        this.okHttpClient = client;
    }

    @Override
    public DataFetcher<InputStream> getResourceFetcher(GlideUrl model, int width, int height) {
        return new OkHttpUrlFetcher(okHttpClient, model);
    }
}
