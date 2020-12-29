package com.hudson.donglingmusic.module.data.fetcher;

import android.support.annotation.NonNull;

import com.hudson.donglingmusic.common.Utils.DateUtils;
import com.hudson.donglingmusic.entity.SongSheet;
import com.hudson.donglingmusic.module.data.requestParam.IRequestParam;
import com.hudson.donglingmusic.module.data.requestParam.SongSheetRequest;

/**
 * 网络歌单获取类
 * Created by Hudson on 2020/3/2.
 */
public class SongSheetFetcher extends CommonDataFetcher<SongSheet> {
    private int mSheetPageNo;
    private int mSheetPageSize;

    public SongSheetFetcher(int sheetPageNo, int sheetPageSize) {
        mSheetPageNo = sheetPageNo;
        mSheetPageSize = sheetPageSize;
    }

    @NonNull
    @Override
    protected IRequestParam getRequestParam() {
        return new SongSheetRequest(mSheetPageNo,mSheetPageSize);
    }

    @NonNull
    @Override
    protected String getUniqueTag() {
        return mSheetPageNo +"&"+ mSheetPageSize+"&"+ DateUtils.getTodayFormatStr();
    }
}
