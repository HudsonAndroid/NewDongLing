package com.hudson.donglingmusic.entity;

import com.hudson.donglingmusic.common.Utils.jsonUtils.annotions.JsonKey;
import com.hudson.donglingmusic.module.data.fetcher.NetMusicInfoFetcher;

import java.util.List;

/**
 * Created by Hudson on 2020/3/5.
 */
public class NetMusicInfo extends BaseResult {
    @JsonKey("songurl")
    private SongUrl mSongUrl;
    @JsonKey("mv_info")
    private MvInfo mMvInfo;
    @JsonKey("share_info")
    private ShareInfo mShareInfo;
    @JsonKey("songinfo")
    private SongInfo mSongInfo;

    public SongUrl getSongUrl() {
        return mSongUrl;
    }

    public MvInfo getMvInfo() {
        return mMvInfo;
    }

    public ShareInfo getShareInfo() {
        return mShareInfo;
    }

    public SongInfo getSongInfo() {
        return mSongInfo;
    }

    public static class ShareInfo{
        @JsonKey("share_url")
        private String mShareUrl;
        @JsonKey("title")
        private String mTitle;
        @JsonKey("share_pic")
        private String mSharePic;
        @JsonKey("from")
        private ShareInfoFrom mFrom;

        public String getShareUrl() {
            return mShareUrl;
        }

        public String getTitle() {
            return mTitle;
        }

        public String getSharePic() {
            return mSharePic;
        }

        public ShareInfoFrom getFrom() {
            return mFrom;
        }
    }

    public static class ShareInfoFrom{
        @JsonKey("title")
        private String mTitle;
    }

    /*
     {
		"resource_type_ext": "0",
		"resource_type": "0",
		"del_status": "0",
		"collect_num": 2476,
		"hot": "3477",
		"res_reward_flag": "0",
		"sound_effect": "",
		"title": "如风往事",
		"language": "国语",
		"play_type": 0,
		"country": "内地",
		"biaoshi": "lossless,perm-3",
		"bitrate_fee": "{\"0\":\"0|0\",\"1\":\"0|0\"}",
		"song_source": "web",
		"is_first_publish": 0,
		"artist_640_1136": "",
		"is_secret": "0",
		"charge": 0,
		"copy_type": "1",
		"share_url": "http:\/\/music.baidu.com\/play\/14541609",
		"has_mv_mobile": 1,
		"album_no": "1",
		"is_charge": "",
		"pic_radio": "http:\/\/qukufile2.qianqian.com\/data2\/pic\/ca2d09f63f8f090041a1fe48d9f4ec9a\/13748427\/13748427.jpg@s_2,w_300,h_300",
		"has_filmtv": "0",
		"pic_huge": "http:\/\/qukufile2.qianqian.com\/data2\/pic\/ca2d09f63f8f090041a1fe48d9f4ec9a\/13748427\/13748427.jpg@s_2,w_1000,h_1000",
		"ting_uid": "1925,56242",
		"expire": 36000,
		"bitrate": "128,320,1411",
		"si_proxycompany": "滚石国际音乐股份有限公司",
		"compose": "卢冠廷",
		"toneid": "600902000001137184",
		"area": "0",
		"info": "",
		"artist_500_500": "http:\/\/qukufile2.qianqian.com\/data2\/pic\/246668318\/246668318.jpg@s_2,w_500,h_500",
		"multiterminal_copytype": "",
		"has_mv": 1,
		"total_listen_nums": "571504",
		"song_id": "14541609",
		"piao_id": "0",
		"high_rate": "320",
		"compress_status": "0",
		"original": 0,
		"artist_480_800": "",
		"relate_status": "0",
		"learn": 1,
		"artist": "李宗盛,卢冠廷",
		"pic_big": "http:\/\/qukufile2.qianqian.com\/data2\/pic\/ca2d09f63f8f090041a1fe48d9f4ec9a\/13748427\/13748427.jpg@s_2,w_150,h_150",
		"artist_list": [{
			"avatar_mini": "http:\/\/qukufile2.qianqian.com\/data2\/pic\/246668318\/246668318.jpg@s_2,w_20,h_20",
			"avatar_s300": "http:\/\/qukufile2.qianqian.com\/data2\/pic\/246668318\/246668318.jpg@s_2,w_300,h_300",
			"ting_uid": "1925",
			"del_status": "0",
			"avatar_s180": "http:\/\/qukufile2.qianqian.com\/data2\/pic\/246668318\/246668318.jpg@s_2,w_180,h_180",
			"artist_name": "李宗盛",
			"avatar_small": "http:\/\/qukufile2.qianqian.com\/data2\/pic\/246668318\/246668318.jpg@s_2,w_48,h_48",
			"avatar_s500": "http:\/\/qukufile2.qianqian.com\/data2\/pic\/246668318\/246668318.jpg@s_2,w_500,h_500",
			"artist_id": "4598"
		}, {
			"avatar_mini": "http:\/\/qukufile2.qianqian.com\/data2\/pic\/55b77154fc1883ca23f9e823f697e1bd\/568676172\/568676172.jpg@s_2,w_20,h_20",
			"avatar_s300": "http:\/\/qukufile2.qianqian.com\/data2\/pic\/55b77154fc1883ca23f9e823f697e1bd\/568676172\/568676172.jpg@s_2,w_300,h_300",
			"ting_uid": "56242",
			"del_status": "0",
			"avatar_s180": "http:\/\/qukufile2.qianqian.com\/data2\/pic\/55b77154fc1883ca23f9e823f697e1bd\/568676172\/568676172.jpg@s_2,w_180,h_180",
			"artist_name": "卢冠廷",
			"avatar_small": "http:\/\/qukufile2.qianqian.com\/data2\/pic\/55b77154fc1883ca23f9e823f697e1bd\/568676172\/568676172.jpg@s_2,w_48,h_48",
			"avatar_s500": "http:\/\/qukufile2.qianqian.com\/data2\/pic\/55b77154fc1883ca23f9e823f697e1bd\/568676172\/568676172.jpg@s_2,w_500,h_500",
			"artist_id": "6186"
		}],
		"comment_num": 31,
		"songwriting": "唐书琛,李宗盛",
		"pic_singer": "",
		"album_1000_1000": "http:\/\/qukufile2.qianqian.com\/data2\/pic\/ca2d09f63f8f090041a1fe48d9f4ec9a\/13748427\/13748427.jpg@s_2,w_1000,h_1000",
		"album_id": "13748427",
		"share_num": 186,
		"album_500_500": "http:\/\/qukufile2.qianqian.com\/data2\/pic\/ca2d09f63f8f090041a1fe48d9f4ec9a\/13748427\/13748427.jpg@s_2,w_500,h_500",
		"aliasname": "",
		"album_title": "我们就是这样",
		"korean_bb_song": "0",
		"author": "李宗盛,卢冠廷",
		"all_artist_id": "4598,6186",
		"file_duration": "268",
		"publishtime": "1993-10-16",
		"versions": "",
		"artist_1000_1000": "http:\/\/qukufile2.qianqian.com\/data2\/pic\/246668318\/246668318.jpg",
		"res_encryption_flag": "0",
		"all_rate": "96,128,224,320,flac",
		"artist_id": "4598",
		"distribution": "0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,1111111111,1111111111,0000000000",
		"lrclink": "http:\/\/qukufile2.qianqian.com\/data2\/lrc\/31520583\/31520583.lrc",
		"pic_small": "http:\/\/qukufile2.qianqian.com\/data2\/pic\/ca2d09f63f8f090041a1fe48d9f4ec9a\/13748427\/13748427.jpg@s_2,w_90,h_90",
		"original_rate": "",
		"havehigh": 2,
		"pic_premium": "http:\/\/qukufile2.qianqian.com\/data2\/pic\/ca2d09f63f8f090041a1fe48d9f4ec9a\/13748427\/13748427.jpg@s_2,w_500,h_500"
	},
     */
    public static class SongInfo extends NetSongInfo{
        @JsonKey("collect_num")
        private int mCollectNum;
        @JsonKey("total_listen_nums")
        private int mListenCount;
        @JsonKey("share_num")
        private int mShareNum;
        @JsonKey("share_url")
        private String mShareUrl;
        @JsonKey("comment_num")
        private int mCommentNum;
        @JsonKey("songwriting")
        private String mSongWriting;
        @JsonKey("artist_list")
        private List<ArtistInfo> mArtistInfos;
        @JsonKey("artist")
        private String mArtist;

        public String getArtist() {
            return mArtist;
        }

        public int getCollectNum() {
            return mCollectNum;
        }

        public int getCommentNum() {
            return mCommentNum;
        }

        public String getSongWriting() {
            return mSongWriting;
        }

        public int getShareNum() {
            return mShareNum;
        }

        public int getListenCount() {
            return mListenCount;
        }

        public String getShareUrl() {
            return mShareUrl;
        }

        public List<ArtistInfo> getArtistInfos() {
            return mArtistInfos;
        }
    }

    public static class ArtistInfo{
        @JsonKey("avatar_mini")
        private String mMiniAvatar;
        @JsonKey("avatar_s300")
        private String mAvatar300;
        @JsonKey("ting_uid")
        private int mTingUid;
        @JsonKey("del_status")
        private int mDelStatus;
        @JsonKey("avatar_s180")
        private String mAvatar180;
        @JsonKey("artist_name")
        private String mName;
        @JsonKey("avatar_small")
        private String mSmallAvatar;
        @JsonKey("avatar_s500")
        private String mAvatar500;
        @JsonKey("artist_mini")
        private int mArtistId;

        public String getMiniAvatar() {
            return mMiniAvatar;
        }

        public String getAvatar300() {
            return mAvatar300;
        }

        public int getTingUid() {
            return mTingUid;
        }

        public int getDelStatus() {
            return mDelStatus;
        }

        public String getAvatar180() {
            return mAvatar180;
        }

        public String getName() {
            return mName;
        }

        public String getSmallAvatar() {
            return mSmallAvatar;
        }

        public String getAvatar500() {
            return mAvatar500;
        }

        public int getArtistId() {
            return mArtistId;
        }
    }

    public static class SongUrl{
        @JsonKey("url")
        private List<SongUrlItem> mSongUrlItems;

        public List<SongUrlItem> getSongUrlItems() {
            return mSongUrlItems;
        }
    }

    /*
    "show_link": "http:\/\/audio04.dmhmusic.com\/71_53_T10041173953_128_4_1_0_sdk-cpm\/cn\/0209\/M00\/2E\/77\/ChR47Fpkth2AET-oAEGbGQ8kv8A255.mp3?xcode=d21960dbc20c01a35f92944ed3853f6f17b2e82",
			"free": 1,
			"replay_gain": "0.580002",
			"hash": "5b9fd5ddffaf0b25c72afc5f043dcddc4d028398",
			"preload": 80,
			"can_load": true,
			"file_format": "mp3",
			"file_bitrate": 128,
			"file_link": "http:\/\/musicapi.qianqian.com\/dmhaudioproxy\/aC\/mMzt\/2GmR2SL0\/KGhy5nVWWnX8hhF+UWbt3c5GNI=",
			"down_type": 1,
			"song_file_id": 0,
			"file_size": 4299545,
			"can_see": 1,
			"file_duration": 267,
			"file_quality": 0,
			"is_udition_url": 0,
			"file_extension": "mp3",
			"original": 0
     */
    public static class SongUrlItem{
        @JsonKey("show_link")
        private String mShowLink;
        @JsonKey("free")
        private int mIsFree;
        @JsonKey("can_load")
        private boolean canLoad;
        @JsonKey("file_format")
        private String mFileFormat;
        @JsonKey("file_bitrate")
        private int mFileBitrate;
        @JsonKey("file_link")
        private String mFileLink;
        @JsonKey("file_size")
        private long mFileSize;
        @JsonKey("can_see")
        private int canSee;
        @JsonKey("file_duration")
        private long mFileDuration;

        public String getShowLink() {
            return mShowLink;
        }

        public int getIsFree() {
            return mIsFree;
        }

        public boolean isCanLoad() {
            return canLoad;
        }

        public String getFileFormat() {
            return mFileFormat;
        }

        public int getFileBitrate() {
            return mFileBitrate;
        }

        public String getFileLink() {
            return mFileLink;
        }

        public long getFileSize() {
            return mFileSize;
        }

        public int getCanSee() {
            return canSee;
        }

        public long getFileDuration() {
            return mFileDuration;
        }
    }

    public static class MvInfo{
        @JsonKey("share_url")
        private String mShareUrl;
        @JsonKey("file_link")
        private String mFileLink;
        @JsonKey("mv_id")
        private int mMvId;

        public String getShareUrl() {
            return mShareUrl;
        }

        public String getFileLink() {
            return mFileLink;
        }

        public int getMvId() {
            return mMvId;
        }
    }

    public static NetMusicInfo fetch(int songId){
        return new NetMusicInfoFetcher(songId).fetchData();
    }
}
