package com.hudson.donglingmusic.module.data.fetcher;

import android.support.annotation.NonNull;

import com.hudson.donglingmusic.common.Utils.DateUtils;
import com.hudson.donglingmusic.entity.Billboard;
import com.hudson.donglingmusic.module.data.requestParam.BillboardRequest;
import com.hudson.donglingmusic.module.data.requestParam.IRequestParam;

/**
 * Created by Hudson on 2020/3/18.
 */
public class BillboardFetcher extends CommonDataFetcher<Billboard> {
    private int mType;
    private int mOffset;
    private int mSize;

    public BillboardFetcher(int type, int offset, int size) {
        mType = type;
        mOffset = offset;
        mSize = size;
    }

    @NonNull
    @Override
    protected IRequestParam getRequestParam() {
        return new BillboardRequest(mType,mOffset,mSize);
    }

    @NonNull
    @Override
    protected String getUniqueTag() {
        return mType + "&" + mOffset + "&" + mSize + "&" + DateUtils.getTodayFormatStr();
    }
}
