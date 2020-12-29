package com.hudson.donglingmusic.entity;

import com.hudson.donglingmusic.common.Utils.jsonUtils.annotions.JsonKey;
import com.hudson.donglingmusic.module.data.fetcher.BillboardFetcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hudson on 2020/3/18.
 */
public class Billboard extends BaseResult{
    //新歌榜
    public static int BILLBOARD_NEW_MUSIC = 1;
    //原创音乐榜
    public static int BILLBOARD_ORIGINAL = 200;
    //热歌榜
    public static int BILLBOARD_HOT_MUSIC = 2;
    //欧美金曲榜
    public static int BILLBOARD_EU_UK = 21;
    //King榜
    public static int BILLBOARD_KING = 100;
    //华语金曲榜
    public static int BILLBOARD_NET_MUSIC = 25;
    //经典老哥榜
    public static int BILLBOARD_CLASSIC_OLD = 22;

    @JsonKey("song_list")
    private List<NetSongInfo> mNetSongInfos;
    private List<MusicEntity> mTransformMusics;
    @JsonKey("billboard")
    private BillboardInfo mBillboardInfo;

    public List<NetSongInfo> getNetSongInfos() {
        return mNetSongInfos;
    }

    public BillboardInfo getBillboardInfo() {
        return mBillboardInfo;
    }

    public List<MusicEntity> getTransformMusics() {
        return mTransformMusics;
    }

    public void transformMusic(){
        if(mNetSongInfos != null && mTransformMusics == null){
            mTransformMusics = new ArrayList<>();
            for (NetSongInfo netSongInfo : mNetSongInfos) {
                mTransformMusics.add(netSongInfo.transformMusicEntity());
            }
        }
    }

    /*
        "billboard_type": "2",
        "billboard_no": "3090",
        "update_date": "2020-03-18",
        "billboard_songnum": "1499",
        "havemore": 1,
        "name": "\u70ed\u6b4c\u699c",
        "comment": "\u8be5\u699c\u5355\u662f\u6839\u636e\u5343\u5343\u97f3\u4e50\u5e73\u53f0\u6b4c\u66f2\u6bcf\u5468\u64ad\u653e\u91cf\u81ea\u52a8\u751f\u6210\u7684\u6570\u636e\u699c\u5355\uff0c\u7edf\u8ba1\u8303\u56f4\u4e3a\u5343\u5343\u97f3\u4e50\u5e73\u53f0\u4e0a\u7684\u5168\u90e8\u6b4c\u66f2\uff0c\u6bcf\u65e5\u66f4\u65b0\u4e00\u6b21",
        "pic_s192": "http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_1452f36a8dc430ccdb8f6e57be6df2ee.jpg",
        "pic_s640": "http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_361aa8612dd9dd8474daf77040f7079d.jpg",
        "pic_s444": "http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/c83d70cf3bc79f3d98ca8e36b8a1cd11728b2988.jpg",
        "pic_s260": "http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/838ba61ea8d3fd1f1326c83c324e251f95ca5f8c.jpg",
        "pic_s210": "http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_734232335ef76f5a05179797875817f3.jpg",
        "web_url": "http:\/\/music.baidu.com\/top\/dayhot",
        "color": "0xDC5900",
        "bg_color": "0xFBEFE6",
        "bg_pic": "http:\/\/business0.qianqian.com\/qianqian\/file\/5bfe5026de816_689.png"
         */
    public static class BillboardInfo{
        @JsonKey("billboard_type")
        private int mBillboardType;
        @JsonKey("billboard_no")
        private int mBillboardNo;
        @JsonKey("update_date")
        private String mUpdateTime;
        @JsonKey("billboard_songnum")
        private int mSongNum;
        @JsonKey("havemore")
        private int mHasMore;
        @JsonKey("name")
        private String mName;
        @JsonKey("comment")
        private String mComment;
        @JsonKey("pic_s192")
        private String mPicS192;
        @JsonKey("pic_s640")
        private String mPicS640;
        @JsonKey("pic_s444")
        private String mPicS444;
        @JsonKey("pic_s260")
        private String mPicS260;
        @JsonKey("pic_s210")
        private String mPicS210;
        @JsonKey("web_url")
        private String mWebUrl;
        @JsonKey("bg_pic")
        private String mBgPic;
        @JsonKey("color")
        private String mColor;
        @JsonKey("bg_color")
        private String mBgColor;

        public int getBillboardType() {
            return mBillboardType;
        }

        public int getBillboardNo() {
            return mBillboardNo;
        }

        public String getUpdateTime() {
            return mUpdateTime;
        }

        public int getSongNum() {
            return mSongNum;
        }

        public int getHasMore() {
            return mHasMore;
        }

        public String getName() {
            return mName;
        }

        public String getComment() {
            return mComment;
        }

        public String getPicS192() {
            return mPicS192;
        }

        public String getPicS640() {
            return mPicS640;
        }

        public String getPicS444() {
            return mPicS444;
        }

        public String getPicS260() {
            return mPicS260;
        }

        public String getPicS210() {
            return mPicS210;
        }

        public String getWebUrl() {
            return mWebUrl;
        }

        public String getBgPic() {
            return mBgPic;
        }

        public String getColor() {
            return mColor;
        }

        public String getBgColor() {
            return mBgColor;
        }
    }

    public String generateSheetUniqueTag(){
        if(mBillboardInfo != null){
            return mBillboardInfo.mBillboardType +"&"+ mBillboardInfo.mBillboardNo + "&"+mBillboardInfo.mName;
        }
        return null;
    }

    public static Billboard fetchBillboard(int type, int offset, int size){
        return fetchBillboard(type,offset,size,false);
    }

    public static Billboard fetchBillboard(int type, int offset, int size,boolean forceServer){
        return new BillboardFetcher(type,offset,size).setForceServer(forceServer).fetchData();
    }

    public static List<Billboard> getBoardList(int eachSize,boolean forceServer){
        List<Billboard> result = new ArrayList<>();
        addBoard(result,BILLBOARD_NEW_MUSIC,eachSize,forceServer);
        addBoard(result,BILLBOARD_HOT_MUSIC,eachSize,forceServer);
        addBoard(result,BILLBOARD_ORIGINAL,eachSize,forceServer);
        addBoard(result,BILLBOARD_KING,eachSize,forceServer);
        addBoard(result,BILLBOARD_EU_UK,eachSize,forceServer);
        addBoard(result,BILLBOARD_NET_MUSIC,eachSize,forceServer);
        addBoard(result,BILLBOARD_CLASSIC_OLD,eachSize,forceServer);
        return result;
    }

    private static void addBoard(List<Billboard> list,int type,int size,boolean forceServer){
        Billboard billboard = fetchBillboard(type, 0, size,forceServer);
        if(billboard.isSuccess()){
            // 由于返回的歌单内容可能是空，因此进行判断
            List<NetSongInfo> netSongInfos = billboard.getNetSongInfos();
            if(netSongInfos != null && netSongInfos.size() > 0){
                list.add(billboard);
            }
        }
    }
}
