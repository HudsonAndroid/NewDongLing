package com.hudson.donglingmusic.entity;

import java.util.List;

/**
 * Created by Hudson on 2020/3/18.
 */
public class HotPageResult {
    private HomePic mHomePic;
    private List<Billboard> mBillboards;

    public HomePic getHomePic() {
        return mHomePic;
    }

    public List<Billboard> getBillboards() {
        return mBillboards;
    }

    public static HotPageResult fetch(int headerSize, int boardSize,boolean forceServer){
        HotPageResult result = new HotPageResult();
        result.mHomePic =  HomePic.fetch(headerSize,forceServer);
        result.mBillboards = Billboard.getBoardList(boardSize,forceServer);
        return result;
    }
}
