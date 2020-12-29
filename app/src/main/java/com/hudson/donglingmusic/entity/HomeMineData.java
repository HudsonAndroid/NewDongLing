package com.hudson.donglingmusic.entity;

import android.util.Log;

import com.hudson.donglingmusic.controller.ModuleManager;
import com.hudson.donglingmusic.persistent.db.interfaces.IUserDb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hudson on 2020/3/21.
 */
public class HomeMineData {
    private SheetDetail mFavoriteSheet;
    private List<SheetDetail> mOtherSheets;
    private Header mHeader;
    // TODO: 2020/3/21 历史记录 最近播放的歌单

    public SheetDetail getFavoriteSheet() {
        return mFavoriteSheet;
    }

    public List<SheetDetail> getOtherSheets() {
        return mOtherSheets;
    }

    public static class Header{

    }

    public static HomeMineData fetch(){
        HomeMineData homeMineData = new HomeMineData();
        IUserDb userDb = ModuleManager.getDbModule().getUserDb();
        homeMineData.mFavoriteSheet = userDb.getFavoriteSheet();
        List<SheetDetail> sheetList = userDb.getSheetList();
        Log.e("hudson","表单为空"+(sheetList == null));
        if(sheetList != null){
            homeMineData.mOtherSheets = new ArrayList<>();
            homeMineData.mOtherSheets.addAll(sheetList);
        }
        return homeMineData;
    }
}
