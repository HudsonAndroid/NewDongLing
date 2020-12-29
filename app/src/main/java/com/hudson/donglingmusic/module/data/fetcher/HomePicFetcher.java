package com.hudson.donglingmusic.module.data.fetcher;

import android.support.annotation.NonNull;

import com.hudson.donglingmusic.common.Utils.DateUtils;
import com.hudson.donglingmusic.entity.HomePic;
import com.hudson.donglingmusic.module.data.requestParam.HomePicRequest;
import com.hudson.donglingmusic.module.data.requestParam.IRequestParam;

/**
 * Created by Hudson on 2020/2/29.
 */
public class HomePicFetcher extends CommonDataFetcher<HomePic> {
    private HomePicRequest mRequest;

    public HomePicFetcher(int picCount) {
        mRequest = new HomePicRequest(picCount);
    }


    @NonNull
    @Override
    protected IRequestParam getRequestParam() {
        return mRequest;
    }

    @NonNull
    @Override
    protected String getUniqueTag() {
        return DateUtils.getTodayFormatStr();
    }
}
