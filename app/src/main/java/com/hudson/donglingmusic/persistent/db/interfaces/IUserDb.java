package com.hudson.donglingmusic.persistent.db.interfaces;

import android.support.annotation.NonNull;

import com.hudson.donglingmusic.entity.MusicEntity;
import com.hudson.donglingmusic.entity.SheetDetail;

import java.util.List;

/**
 * Created by Hudson on 2020/3/20.
 */
public interface IUserDb extends IWriteableDb {
    SheetDetail getFavoriteSheet();
    boolean addFavoriteMusic(@NonNull MusicEntity entity);
    boolean removeFromFavorite(@NonNull MusicEntity entity);
    boolean isFavoriteMusic(@NonNull MusicEntity entity);
    List<SheetDetail> getSheetList();
    SheetDetail getSheet(String sheetId);
}
