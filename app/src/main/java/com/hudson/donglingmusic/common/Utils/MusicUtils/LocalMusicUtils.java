package com.hudson.donglingmusic.common.Utils.MusicUtils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.hudson.donglingmusic.common.Utils.StorageUtils;
import com.hudson.donglingmusic.entity.MusicEntity;

import java.util.ArrayList;


public class LocalMusicUtils {
    private static final String LYRICS_FILE_SUFFIX = ".lrc";

    @Nullable
    public static String getMusicLocalLyricsFilePath(@NonNull MusicEntity entity){
        if(entity.isNetMusic()){
            return getMusicLocalLyricsFilePath(entity.getArtist()
                    + "-" + entity.getTitle());
        }
        return getMusicLocalLyricsFilePath(entity.getPath());
    }

    @Nullable
    private static String getMusicLocalLyricsFilePath(String musicNameOrPath) {
        String lyricsPath = null;
        if(!TextUtils.isEmpty(musicNameOrPath)){
            int fileDivider = musicNameOrPath.lastIndexOf("/");
            int typeDivider = musicNameOrPath.lastIndexOf(".");
            if(isValidDivider(fileDivider,musicNameOrPath)
                    && isValidDivider(typeDivider,musicNameOrPath)
                    && fileDivider + 1 < typeDivider){
                lyricsPath = musicNameOrPath.substring(fileDivider+1,typeDivider) + LYRICS_FILE_SUFFIX;
                lyricsPath = StorageUtils.getLyricsPath() + lyricsPath;
            }else{
                // 作为name
                lyricsPath = StorageUtils.getLyricsPath() + musicNameOrPath + LYRICS_FILE_SUFFIX;
            }
        }
        return lyricsPath;
    }

    private static boolean isValidDivider(int divider,@NonNull String target){
        return divider > 0 && divider < target.length() - 1;
    }

    public static ArrayList<MusicEntity> getLocalMusics(Context context) {
        ArrayList<MusicEntity> musicList = null;
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Audio.Media._ID, // int
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.DATA, // String
                        MediaStore.Audio.Media.DISPLAY_NAME, // String
                        MediaStore.Audio.Media.MIME_TYPE, // String
                        MediaStore.Audio.Media.ALBUM_ID // int
                },
                MediaStore.Audio.Media.IS_MUSIC + " = 1 AND "
                        + MediaStore.Audio.Media.DURATION + " > 10000",
                null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (cursor != null) {
            musicList = new ArrayList<>();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                MusicEntity temp = new MusicEntity();
                temp.setSongId(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
                temp.setAlbumTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
                temp.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                temp.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
                temp.setDuration(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
                temp.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                temp.setAlbumId(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
                musicList.add(temp);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return musicList;
    }
}
