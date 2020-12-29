package com.hudson.donglingmusic.entity;

import com.hudson.donglingmusic.common.Utils.jsonUtils.annotions.JsonKey;
import com.hudson.donglingmusic.module.data.fetcher.HomePicFetcher;

import java.util.List;

/**
 * Created by Hudson on 2020/2/29.
 */
public class HomePic extends BaseResult {
    @JsonKey("pic")
    private List<PicItem> mPics;

    public List<PicItem> getPics() {
        return mPics;
    }

    /*
        "type": 2,
        "mo_type": 2,
        "code": "674657880",
        "randpic": "http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_1582952661d306238b85d1319bae0999b87a0c62d3.jpg",
        "randpic_ios5": "",
        "randpic_desc": "",
        "randpic_ipad": "",
        "randpic_qq": "",
        "randpic_2": "",
        "randpic_iphone6": "",
        "special_type": 0,
        "ipad_desc": "",
        "is_publish": "111111"
      */
    public static class PicItem{
        @JsonKey("type")
        private int mType;
        @JsonKey("mo_type")
        private int mMoType;
        @JsonKey("code")
        private int mCode;
        @JsonKey("randpic")
        private String mUrl;

        public String getUrl() {
            return mUrl;
        }
    }

    public static HomePic fetch(int picCount){
        return fetch(picCount,false);
    }

    public static HomePic fetch(int picCount,boolean forceServer){
        return new HomePicFetcher(picCount).setForceServer(forceServer).fetchData();
    }
}
