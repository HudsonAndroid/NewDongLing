package com.hudson.donglingmusic.module.data.requestParam;

/**
 * 获取音乐榜歌曲
 * Created by Hudson on 2020/3/18.
 */
public class BillboardRequest extends BaseRequest {
    private int mType;
    private int mOffset;
    private int mSize;

    public BillboardRequest(int type, int offset, int size) {
        mOffset = offset;
        mSize = size;
        mType = type;
    }

    @Override
    protected String getMethod() {
        return "baidu.ting.billboard.billList";
    }

    @Override
    protected String getExtendParam() {
        return "&type="+mType+"&offset="+mOffset+"&size="+mSize;
    }
}
