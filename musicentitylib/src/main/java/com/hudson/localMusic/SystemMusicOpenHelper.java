package com.hudson.localMusic;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.hudson.musicentitylib.MusicEntity;

import java.util.ArrayList;


public class SystemMusicOpenHelper {

	public static ArrayList<MusicEntity> queryMusics(Context context) {
	    ArrayList<MusicEntity> musicList = null;
	    Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
	            new String[] {
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
	    if(cursor != null){
	    	musicList = new ArrayList<>();
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				MusicEntity temp = new MusicEntity();
			temp.setSongId(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID))+"");
			temp.setAlbumName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
	        temp.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
	        temp.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
	        temp.setDuration(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
	        temp.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
	        temp.setAlbumId(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))+"");
				musicList.add(temp);
				cursor.moveToNext();
			}
			cursor.close();
		}
	    return musicList;
	}
}
