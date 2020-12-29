package com.hudson.donglingmusic.entity;

/*
        "title": "\u65f6\u5149\u4e4b\u57ce",
            "del_status": "0",
            "distribution": "0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000",
            "song_id": "540056023",
            "author": "\u90d1\u5174\u7426",
            "album_id": "522740227",
            "album_title": "\u6781\u5149",
            "relate_status": "0",
            "all_rate": "96,128,224,320,flac",
            "high_rate": "320",
            "all_artist_id": "243053581",
            "copy_type": "1",
            "has_mv": 0,
            "toneid": "0",
            "resource_type": "0",
            "is_ksong": "0",
            "resource_type_ext": "2",
            "versions": "",
            "bitrate_fee": "{\"0\":\"129|-1\",\"1\":\"-1|-1\"}",
            "biaoshi": "lossless,vip,perm-1",
            "file_duration": "223",
            "pic_radio": "http:\/\/qukufile2.qianqian.com\/data2\/pic\/d8a5236af2b857da5e0bad79a2a8a5e8\/522740227\/522740227.jpg@s_2,w_300,h_300",
            "pic_s130": "http:\/\/qukufile2.qianqian.com\/data2\/pic\/d8a5236af2b857da5e0bad79a2a8a5e8\/522740227\/522740227.jpg@s_2,w_130,h_130",
            "pic_big": "http:\/\/qukufile2.qianqian.com\/data2\/pic\/d8a5236af2b857da5e0bad79a2a8a5e8\/522740227\/522740227.jpg@s_2,w_150,h_150",
            "has_mv_mobile": 0,
            "ting_uid": "210119208",
            "is_first_publish": 0,
            "havehigh": 2,
            "charge": 0,
            "learn": 0,
            "song_source": "web",
            "piao_id": "0",
            "korean_bb_song": "0",
            "mv_provider": "0000000000",
            "share": "http:\/\/music.baidu.com\/song\/540056023"
         */

import android.text.TextUtils;

import com.hudson.dongling.db.gen.DaoSession;
import com.hudson.dongling.db.gen.MusicEntityDao;
import com.hudson.dongling.db.gen.SheetDetailDao;
import com.hudson.donglingmusic.common.Utils.jsonUtils.annotions.JsonIgnore;
import com.hudson.donglingmusic.common.Utils.jsonUtils.annotions.JsonKey;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;

/**
 * Created by Hudson on 2020/3/5.
 */
@Entity
public class MusicEntity {
    @Id(autoincrement = true)
    private Long id;
    @JsonKey("title")
    private String title;
    @JsonKey("del_status")
    private int delStatus;
    @JsonKey("distribution")
    private String distribution;
    @JsonKey("song_id")
    private int songId;
    @JsonKey("author")
    private String artist;
    @JsonKey("album_id")
    private int albumId;
    @JsonKey("album_title")
    private String albumTitle;
    @JsonKey("relate_status")
    private int relateStatus;
    @JsonKey("all_rate")
    private String allRate;
    @JsonKey("high_rate")
    private int highRate;
    @JsonKey("all_artist_id")
    private int allArtistId;
    @JsonKey("copy_type")
    private int copyType;
    @JsonKey("has_mv")
    private int hasMv;
    @JsonKey("toneid")
    private int toneId;
    @JsonKey("resource_type")
    private int resourceType;
    @JsonKey("is_ksong")
    private int isKSong;
    @JsonKey("resource_type_ext")
    private int resourceTypeExt;
    @JsonKey("versions")
    private String version;
    @JsonKey("biaoshi")
    private String tag;
    @JsonKey("file_duration")
    private long duration;
    @JsonKey("pic_radio")
    private String picRadio;
    @JsonKey("pic_s130")
    private String picS130;
    @JsonKey("pic_big")
    private String picBig;
    @JsonKey("ting_uid")
    private int tingUid;
    @JsonKey("song_source")
    private String songSource;
    @JsonKey("share")
    private String shareUrl;
    // 榜单列表中会给歌词地址，但通过songId获取到的netMusicInfo中没有了歌词地址，因此额外附加一个地址保存榜单列表歌词
    @JsonIgnore
    private String lyricsLink;
    //多对多
    @ToMany
    // 连接两个多对多实体类的中间类
    @JoinEntity(
            // 中间类类名
            entity = JoinSheetWithMusic.class,
            // 源属性，中间类的外键，对应Course类的主键
            sourceProperty = "mId",
            // 目标属性，中间类的外键，对应Teacher类的主键
            targetProperty = "sId"
    )
    private List<SheetDetail> sheets;// 所在的歌单
    //以上是网络字段，以下是网络返回中不存在的字段
    // 路径，本地音乐则存在，网络音乐需要额外解析
    private String path;
    private boolean isNetMusic = false;
    @Unique
    private String uniqueTag;// 用于数据库区分的唯一标识
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 411754295)
    private transient MusicEntityDao myDao;


    @Generated(hash = 2096982947)
    public MusicEntity(Long id, String title, int delStatus, String distribution, int songId, String artist, int albumId, String albumTitle,
            int relateStatus, String allRate, int highRate, int allArtistId, int copyType, int hasMv, int toneId, int resourceType, int isKSong,
            int resourceTypeExt, String version, String tag, long duration, String picRadio, String picS130, String picBig, int tingUid,
            String songSource, String shareUrl, String lyricsLink, String path, boolean isNetMusic, String uniqueTag) {
        this.id = id;
        this.title = title;
        this.delStatus = delStatus;
        this.distribution = distribution;
        this.songId = songId;
        this.artist = artist;
        this.albumId = albumId;
        this.albumTitle = albumTitle;
        this.relateStatus = relateStatus;
        this.allRate = allRate;
        this.highRate = highRate;
        this.allArtistId = allArtistId;
        this.copyType = copyType;
        this.hasMv = hasMv;
        this.toneId = toneId;
        this.resourceType = resourceType;
        this.isKSong = isKSong;
        this.resourceTypeExt = resourceTypeExt;
        this.version = version;
        this.tag = tag;
        this.duration = duration;
        this.picRadio = picRadio;
        this.picS130 = picS130;
        this.picBig = picBig;
        this.tingUid = tingUid;
        this.songSource = songSource;
        this.shareUrl = shareUrl;
        this.lyricsLink = lyricsLink;
        this.path = path;
        this.isNetMusic = isNetMusic;
        this.uniqueTag = uniqueTag;
    }

    @Generated(hash = 1380251324)
    public MusicEntity() {
    }


    // 下列set方法仅本地歌曲使用
    public void setTitle(String title) {
        this.title = title;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * 配置是否是网络歌曲
     * @param netMusic
     */
    public void setNetMusic(boolean netMusic) {
        isNetMusic = netMusic;
    }

    public String getTitle() {
        return title;
    }

    public int getSongId() {
        return songId;
    }

    public String getArtist() {
        return artist;
    }

    public int getAlbumId() {
        return albumId;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public int getRelateStatus() {
        return relateStatus;
    }

    public String getAllRate() {
        return allRate;
    }

    public int getHighRate() {
        return highRate;
    }

    public int getAllArtistId() {
        return allArtistId;
    }

    public int getHasMv() {
        return hasMv;
    }

    public int getResourceType() {
        return resourceType;
    }

    public String getTag() {
        return tag;
    }

    /**
     * 被greenDao使用，获取歌曲长度调用${@link #getSongDuration()}
     * @return
     */
    public long getDuration() {
        return duration;
    }

    public long getSongDuration(){
        if(isNetMusic()){
            return duration * 1000;
        }
        return duration;
    }

    public String getPicRadio() {
        return picRadio;
    }

    public String getPicS130() {
        return picS130;
    }

    public String getPicBig() {
        return picBig;
    }

    public int getTingUid() {
        return tingUid;
    }

    public String getSongSource() {
        return songSource;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public String getPath() {
        return path;
    }

    public boolean isNetMusic() {
        return isNetMusic;
    }

    public void generateUniqueTag(){
        if(TextUtils.isEmpty(this.uniqueTag)){
            this.uniqueTag = songId + "&" + title + "&" + artist;
        }
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDelStatus() {
        return this.delStatus;
    }

    public void setDelStatus(int delStatus) {
        this.delStatus = delStatus;
    }

    public String getDistribution() {
        return this.distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public void setRelateStatus(int relateStatus) {
        this.relateStatus = relateStatus;
    }

    public void setAllRate(String allRate) {
        this.allRate = allRate;
    }

    public void setHighRate(int highRate) {
        this.highRate = highRate;
    }

    public void setAllArtistId(int allArtistId) {
        this.allArtistId = allArtistId;
    }

    public int getCopyType() {
        return this.copyType;
    }

    public void setCopyType(int copyType) {
        this.copyType = copyType;
    }

    public void setHasMv(int hasMv) {
        this.hasMv = hasMv;
    }

    public int getToneId() {
        return this.toneId;
    }

    public void setToneId(int toneId) {
        this.toneId = toneId;
    }

    public void setResourceType(int resourceType) {
        this.resourceType = resourceType;
    }

    public int getIsKSong() {
        return this.isKSong;
    }

    public void setIsKSong(int isKSong) {
        this.isKSong = isKSong;
    }

    public int getResourceTypeExt() {
        return this.resourceTypeExt;
    }

    public void setResourceTypeExt(int resourceTypeExt) {
        this.resourceTypeExt = resourceTypeExt;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setPicRadio(String picRadio) {
        this.picRadio = picRadio;
    }

    public void setPicS130(String picS130) {
        this.picS130 = picS130;
    }

    public void setPicBig(String picBig) {
        this.picBig = picBig;
    }

    public void setTingUid(int tingUid) {
        this.tingUid = tingUid;
    }

    public void setSongSource(String songSource) {
        this.songSource = songSource;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public boolean getIsNetMusic() {
        return this.isNetMusic;
    }

    public void setIsNetMusic(boolean isNetMusic) {
        this.isNetMusic = isNetMusic;
    }

    public String getUniqueTag() {
        return this.uniqueTag;
    }

    public void setUniqueTag(String uniqueTag) {
        this.uniqueTag = uniqueTag;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 256537083)
    public List<SheetDetail> getSheets() {
        if (sheets == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SheetDetailDao targetDao = daoSession.getSheetDetailDao();
            List<SheetDetail> sheetsNew = targetDao._queryMusicEntity_Sheets(id);
            synchronized (this) {
                if (sheets == null) {
                    sheets = sheetsNew;
                }
            }
        }
        return sheets;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 658788701)
    public synchronized void resetSheets() {
        sheets = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1260846819)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMusicEntityDao() : null;
    }

    public String getLyricsLink() {
        return this.lyricsLink;
    }

    public void setLyricsLink(String lyricsLink) {
        this.lyricsLink = lyricsLink;
    }
}
