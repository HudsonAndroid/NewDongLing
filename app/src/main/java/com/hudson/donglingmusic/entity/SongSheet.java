package com.hudson.donglingmusic.entity;

import com.hudson.donglingmusic.common.Utils.jsonUtils.annotions.JsonKey;
import com.hudson.donglingmusic.module.data.fetcher.SongSheetFetcher;

import java.util.List;

/**
 * Created by Hudson on 2020/3/2.
 */
public class SongSheet extends BaseResult{
    private static final int STATE_HAS_MORE = 1;

    @JsonKey("total")
    private int mTotal;
    @JsonKey("havemore")
    private int hasMore;
    @JsonKey("content")
    private List<SheetItem> mSongSheets;

    public int getTotal() {
        return mTotal;
    }

    public boolean hasMore() {
        return hasMore == STATE_HAS_MORE;
    }

    public List<SheetItem> getSongSheets() {
        return mSongSheets;
    }

    public static class SheetItem {
        @JsonKey("listid")
        private int mListId;
        @JsonKey("listenum")
        private int mListenNum;
        @JsonKey("collectnum")
        private int mCollectNum;
        @JsonKey("title")
        private String mTitle;
        @JsonKey("pic_300")
        private String mPic300;
        @JsonKey("pic_w300")
        private String mPicW300;
        @JsonKey("tag")
        private String mTag;
        @JsonKey("desc")
        private String mDesc;
        @JsonKey("width")
        private int mWidth;
        @JsonKey("height")
        private int mHeight;
        @JsonKey("songIds")
        private List<Integer> mSongIds;

        public int getListId() {
            return mListId;
        }

        public int getListenNum() {
            return mListenNum;
        }

        public int getCollectNum() {
            return mCollectNum;
        }

        public String getTitle() {
            return mTitle;
        }

        public String getPic300() {
            return mPic300;
        }

        public String getPicW300() {
            return mPicW300;
        }

        public String getTag() {
            return mTag;
        }

        public String getDesc() {
            return mDesc;
        }

        public int getWidth() {
            return mWidth;
        }

        public int getHeight() {
            return mHeight;
        }

        public List<Integer> getSongIds() {
            return mSongIds;
        }
    }

    public static SongSheet fetchData(int sheetPageNo,int sheetPageSize){
        return fetchData(sheetPageNo,sheetPageSize,false);
    }

    public static SongSheet fetchData(int sheetPageNo,int sheetPageSize,boolean forceServer){
        return new SongSheetFetcher(sheetPageNo,sheetPageSize).setForceServer(forceServer).fetchData();
    }
}
