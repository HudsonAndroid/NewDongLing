package com.hudson.donglingmusic.module.data.requestParam;

/**
 * Created by Hudson on 2020/3/2.
 */
public class SongSheetRequest extends BaseRequest {
    private int mPageNo;//页码
    private int mPageSize;//每页数量

    public SongSheetRequest( int pageNo,int pageSize) {
        mPageSize = pageSize;
        mPageNo = pageNo;
    }

    @Override
    protected String getMethod() {
        return "baidu.ting.diy.gedan";
    }

    @Override
    protected String getExtendParam() {
        return "&page_size="+mPageSize + "&page_no="+mPageNo;
    }
}
