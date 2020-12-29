package com.hudson.donglingmusic.common.Utils;

import android.os.Environment;
import android.support.annotation.Nullable;

import java.io.File;

/**
 * Created by Hudson on 2019/11/10.
 */
public class StorageUtils {

    private static final String APP_ROOT_PATH = "/donglingMusic/";//app的默认根路径,不含sd卡绝对路径
    private static final String APP_LYRICS_PATH = APP_ROOT_PATH + "Lyrics/";//app歌词路径
    private static final String APP_DOWNLOAD_PATH = APP_ROOT_PATH + "download/";//app下载路径
    private static final String APP_MUSIC_PIC_PATH = APP_ROOT_PATH + "albumbg/";//app音乐图片路径
    private static final String APP_LYRICS_FONTS_PATH = APP_ROOT_PATH + "fonts/";//app歌词字体路径

    @Nullable
    public static String getLyricsPath(){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            String result = sdPath + APP_LYRICS_PATH;
            return checkDir(result);
        }
        return null;
    }

    public static String getLocalMusicPicPath(){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            String result = sdPath + APP_MUSIC_PIC_PATH;
            return checkDir(result);
        }
        return null;
    }

    private static String checkDir(String path){
        File file = new File(path);
        if(!file.exists()){
            if(file.mkdirs()){
                return path;
            }
        }
        return path;
    }
}
