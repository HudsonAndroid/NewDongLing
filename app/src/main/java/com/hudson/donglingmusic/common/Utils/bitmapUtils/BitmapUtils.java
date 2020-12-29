package com.hudson.donglingmusic.common.Utils.bitmapUtils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.common.Utils.CommonUtils;
import com.hudson.donglingmusic.common.Utils.StorageUtils;
import com.hudson.donglingmusic.entity.MusicEntity;
import com.hudson.donglingmusic.global.DongLingApplication;

import java.io.IOException;
import java.io.InputStream;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by Hudson on 2020/4/19.
 */
public class BitmapUtils {
    public static final int MAX_BLUR = 25;
    public static final int MIN_BLUR = 1;

    public static void loadNetBitmap(final View view, String url, int radius,boolean dark){
        Context context = view.getContext();
        if(!CommonUtils.isActivityDestroy(context)){
            Glide.with(context)
                    .load(url)
                    .bitmapTransform(new BlurTransformation(context,radius),
                            new DarkBitmap(context,dark),
                            new CenterCrop(context))
                    .into(new ViewTarget<View,GlideDrawable>(view) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            attachViewPic(view,resource);
                        }
                    });
        }
    }

    private static void attachViewPic(View view,Drawable resource) {
        if(view instanceof ImageView){
            ((ImageView) view).setImageDrawable(resource);
        }else{
            view.setBackground(resource);
        }
    }

    public static void loadMusicPic(@NonNull MusicEntity music,
                                    @NonNull View target){
        loadMusicPic(music,target,1,false, R.drawable.icon_player);
    }

    public static <Y extends Target<Bitmap>> void loadMusicPicBitmapType(@NonNull MusicEntity music,
                                                                      @NonNull Context context,
                                                                         int width,int height,Y target){
        loadMusicPicBitmapType(music,context,width,height,MIN_BLUR,false, R.drawable.icon_player,target);
    }

    public static void loadMusicPic(@NonNull MusicEntity music,
                                    @NonNull View view,
                                    @IntRange(from = 1, to = 25) int blurRadius,
                                    boolean dark,
                                    int defaultId){
        Context context = view.getContext();
        if(!CommonUtils.isActivityDestroy(context)){
            Glide.with(context)
                    .load(music)
                    .bitmapTransform(new BlurTransformation(context,blurRadius),
                            new DarkBitmap(context,dark),
                            new CenterCrop(context))
                    .error(defaultId)
                    .placeholder(defaultId)
                    .into(new ViewTarget<View,GlideDrawable>(view) {
                        @Override
                        public void onResourceReady(GlideDrawable resource,
                                                    GlideAnimation<? super GlideDrawable> glideAnimation) {
                            attachViewPic(view,resource);
                        }

                        //需要重写该方法，因为加载失败的情况下，ViewTarget是不会有任何操作的，因为她不知道怎么处理
                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            attachViewPic(view,errorDrawable);
                        }
                    });
        }
    }

    public static <Y extends Target<Bitmap>> void loadMusicPicBitmapType(@NonNull MusicEntity music,
                                                                      @NonNull Context context,
                                                                      int width,int height,
                                                                      @IntRange(from = 1, to = 25) int blurRadius,
                                                                      boolean dark,
                                                                      int defaultId,Y target){
        if(!CommonUtils.isActivityDestroy(context)){
            Glide.with(context)
                    .load(music)
                    .asBitmap()
                    .override(width,height)
                    .transform(new BlurTransformation(context,blurRadius),
                            new DarkBitmap(context,dark),
                            new CenterCrop(context))
                    .error(defaultId)
                    .placeholder(defaultId)
                    .into(target);
        }
    }

    /**
     * 获取专辑封面位图对象
     *
     * @param title 歌曲的标题
     * @param albumId 专辑Id
     * @return
     */
    public static Bitmap getArtwork(String title, long albumId) {
        Context context = DongLingApplication.getGlobalContext();
        //从系统数据库中获取
        Uri uri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"),
                albumId);
        if (uri != null) {
            InputStream in = null;
            ContentResolver contentResolver = context.getContentResolver();
            try {
                in = contentResolver.openInputStream(uri);
                return BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
                return getAppMusicPicFromDirectory(title);
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return getAppMusicPicFromDirectory(title);
    }

    /**
     * 从本地的app文件夹中获取背景图
     */
    private static Bitmap getAppMusicPicFromDirectory(String netMusicTitle){

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        options.inJustDecodeBounds = true;
        String localMusicPicPath = StorageUtils.getLocalMusicPicPath();
        if(localMusicPicPath != null){
            String pathName = localMusicPicPath + netMusicTitle + ".jpg";
            return BitmapFactory.decodeFile(pathName);
        }
        return null;
    }

    @Nullable
    public static Bitmap getViewBitmap(View view){
        try{
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            Bitmap copy = view.getDrawingCache()
                    .copy(Bitmap.Config.ARGB_4444, true);
            view.destroyDrawingCache();
            return copy;
        }catch (OutOfMemoryError e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
