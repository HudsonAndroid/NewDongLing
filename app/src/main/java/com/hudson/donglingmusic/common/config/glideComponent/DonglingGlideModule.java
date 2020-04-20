package com.hudson.donglingmusic.common.config.glideComponent;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.hudson.donglingmusic.common.config.glideComponent.musicPic.MusicUrlModelLoader;
import com.hudson.donglingmusic.entity.MusicEntity;

import java.io.InputStream;

/**
 * Created by Hudson on 2020/4/20.
 */
public class DonglingGlideModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(GlideUrl.class, InputStream.class, new OkHttpGlideUrlModelLoader.Factory());
        glide.register(MusicEntity.class,InputStream.class,new MusicUrlModelLoader.Factory());
    }
}
