package com.hudson.donglingmusic.module.data.requestParam;

/**
 * Created by Hudson on 2020/3/3.
 */
public class SheetDetailRequest extends BaseRequest {
    private String mSheetId;

    public SheetDetailRequest(String sheetId) {
        mSheetId = sheetId;
    }

    @Override
    protected String getMethod() {
        return "baidu.ting.diy.gedanInfo";
    }

    @Override
    protected String getExtendParam() {
        return "&listid="+mSheetId;
    }
}
