package com.hudson.donglingmusic.persistent.db.impl;

import android.support.annotation.NonNull;

import com.hudson.dongling.db.gen.JoinSheetWithMusicDao;
import com.hudson.dongling.db.gen.MusicEntityDao;
import com.hudson.dongling.db.gen.SheetDetailDao;
import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.common.Utils.CommonUtils;
import com.hudson.donglingmusic.entity.JoinSheetWithMusic;
import com.hudson.donglingmusic.entity.MusicEntity;
import com.hudson.donglingmusic.entity.SheetDetail;
import com.hudson.donglingmusic.persistent.db.UserDb;
import com.hudson.donglingmusic.persistent.db.interfaces.IUserDb;

import java.util.List;

import static com.hudson.donglingmusic.entity.SheetDetail.DEFAULT_FAVORITE_SHEET_ID;

/**
 * Created by Hudson on 2020/3/20.
 */
public class UserDbImpl extends BaseWriteableDb<UserDb> implements IUserDb {
    public UserDbImpl() {
        super(UserDb.getInstance());
        init();
    }

    private void init(){
        // 创建我喜欢的歌单
        SheetDetail sheet = mDB.query(SheetDetail.class, SheetDetailDao.Properties.SheetId.eq(DEFAULT_FAVORITE_SHEET_ID));
        if(sheet == null){
            sheet = new SheetDetail();
            sheet.setSheetId(DEFAULT_FAVORITE_SHEET_ID);
            sheet.setTitle(CommonUtils.getString(R.string.sheet_favorite));
            insertOrUpdate(sheet);
        }
    }

    @Override
    public SheetDetail getFavoriteSheet() {
        return getSheet(DEFAULT_FAVORITE_SHEET_ID);
    }

    @Override
    public boolean addFavoriteMusic(MusicEntity entity) {
        entity.generateUniqueTag();// 创建数据库唯一标识
        insertOrUpdate(entity);
        JoinSheetWithMusic tmp = new JoinSheetWithMusic();
        tmp.setMId(entity.getId());
        //更新歌单的图片
        // TODO: 2020/3/22 可以考虑自定义图片，这里是使用最新一首歌曲的图片
        SheetDetail favoriteSheet = getFavoriteSheet();
        favoriteSheet.setPic300(entity.getPicBig());
        tmp.setSId(favoriteSheet.getId());
        tmp.generateUniqueTag();
        insertOrUpdate(favoriteSheet);
        boolean result = insertOrUpdate(tmp);
//        favoriteSheet.resetSongs();
//        favoriteSheet.recoverFlag();
        mDB.clearCache();// GreenDao本身查询自带缓冲，但是如果我们插入了新的数据，查询时还是使用缓冲的
        return result;
    }

    @Override
    public boolean removeFromFavorite(@NonNull MusicEntity entity) {
        SheetDetail favoriteSheet = getFavoriteSheet();
        JoinSheetWithMusic tmp = mDB.query(JoinSheetWithMusic.class, JoinSheetWithMusicDao
                .Properties.UniqueTag.eq(JoinSheetWithMusic.generateUniqueTag(favoriteSheet,entity)));
        entity.generateUniqueTag();
        MusicEntity dbEntity = mDB.query(MusicEntity.class,MusicEntityDao.Properties.UniqueTag.eq(entity.getUniqueTag()));
        if(tmp != null){
            delete(tmp);
            dbEntity.resetSheets();
            favoriteSheet.resetSongs();
            favoriteSheet.recoverFlag();
            boolean canRemoveMusic = true;
            List<SheetDetail> sheets = dbEntity.getSheets();
            if(sheets != null && sheets.size() > 0){
                canRemoveMusic = false;
            }
            if(canRemoveMusic){
                delete(dbEntity);
            }
            // 切换歌单的图片
            List<MusicEntity> songs = favoriteSheet.getSongs();
            String sheetPic;
            if(songs != null && songs.size() > 0){
                sheetPic = songs.get(songs.size() - 1).getPicBig();
            }else{
                sheetPic = null;
            }
            favoriteSheet.setPic300(sheetPic);
            return true;
        }
        return false;
    }

    @Override
    public boolean isFavoriteMusic(@NonNull MusicEntity entity) {
        entity.generateUniqueTag();
        MusicEntity target = mDB.query(MusicEntity.class, MusicEntityDao.Properties.UniqueTag.eq(entity.getUniqueTag()));
        if(target != null){
            entity.setId(target.getId());
            List<SheetDetail> sheets = target.getSheets();
            for (SheetDetail sheet : sheets) {
                if(DEFAULT_FAVORITE_SHEET_ID.equals(sheet.getSheetId())){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<SheetDetail> getSheetList() {
        return mDB.queryAll(SheetDetail.class);
    }

    @Override
    public SheetDetail getSheet(String sheetId) {
        SheetDetail result = mDB.query(SheetDetail.class, SheetDetailDao.Properties.SheetId.eq(sheetId));
        if(result.getSongs() != null){
            result.reverseSongs(); // 后面添加的放在最前面
        }
        return result;
    }
}
