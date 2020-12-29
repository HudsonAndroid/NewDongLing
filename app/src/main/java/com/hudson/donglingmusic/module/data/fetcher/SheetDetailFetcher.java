package com.hudson.donglingmusic.module.data.fetcher;

import android.support.annotation.NonNull;

import com.hudson.donglingmusic.entity.SheetDetail;
import com.hudson.donglingmusic.module.data.requestParam.IRequestParam;
import com.hudson.donglingmusic.module.data.requestParam.SheetDetailRequest;

/**
 * Created by Hudson on 2020/3/3.
 */
public class SheetDetailFetcher extends CommonDataFetcher<SheetDetail> {
    private String mSheetId;

    public SheetDetailFetcher(String sheetId) {
        mSheetId = sheetId;
    }

    @NonNull
    @Override
    protected IRequestParam getRequestParam() {
        return new SheetDetailRequest(mSheetId);
    }

    @NonNull
    @Override
    protected String getUniqueTag() {
        return String.valueOf(mSheetId);
    }

    @Override
    protected boolean isDataValid(@NonNull SheetDetail sheetDetail) {
        return sheetDetail.isSuccess();
    }
}
