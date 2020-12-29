package com.hudson.donglingmusic.entity;

import com.hudson.dongling.db.gen.DaoSession;
import com.hudson.dongling.db.gen.MusicEntityDao;
import com.hudson.dongling.db.gen.SheetDetailDao;
import com.hudson.donglingmusic.common.Utils.jsonUtils.annotions.JsonIgnore;
import com.hudson.donglingmusic.common.Utils.jsonUtils.annotions.JsonKey;
import com.hudson.donglingmusic.controller.ModuleManager;
import com.hudson.donglingmusic.module.data.fetcher.SheetDetailFetcher;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * 歌单
 * Created by Hudson on 2020/3/3.
 */
@Entity
public class SheetDetail extends BaseResult{
    // 我喜欢的歌单id
    public static final String DEFAULT_FAVORITE_SHEET_ID = "0x19941002";
    @Id(autoincrement = true)
    @JsonIgnore
    private Long id;
    @Transient
    private boolean hasReverse = false;

    @Unique
    @JsonKey("listid")
    private String sheetId = UUID.randomUUID().toString();
    @JsonKey("title")
    private String title;
    @JsonKey("pic_300")
    private String pic300;//默认图片地址
    @JsonKey("pic_500")
    private String pic500;
    @JsonKey("pic_w700")
    private String picW700;
    @JsonKey("width")
    private int width;
    @JsonKey("height")
    private int height;
    @JsonKey("listenum")
    private int listenCount;
    @JsonKey("collectnum")
    private int collectCount;
    @JsonKey("tag")
    private String tag;
    @JsonKey("desc")
    private String desc;
    @JsonKey("url")
    private String webUrl;
    @ToMany
    // 连接两个多对多实体类的中间类
    @JoinEntity(
            // 中间类类名
            entity = JoinSheetWithMusic.class,
            // 源属性，中间类的外键，对应CustomSongSheet类的主键
            sourceProperty = "sId",
            // 目标属性，中间类的外键，对应MusicEntity类的主键
            targetProperty = "mId"
    )
    @JsonKey("content")
    private List<MusicEntity> songs;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 807538123)
    private transient SheetDetailDao myDao;

    @Generated(hash = 927435543)
    public SheetDetail(Long id, String sheetId, String title, String pic300, String pic500,
            String picW700, int width, int height, int listenCount, int collectCount, String tag,
            String desc, String webUrl) {
        this.id = id;
        this.sheetId = sheetId;
        this.title = title;
        this.pic300 = pic300;
        this.pic500 = pic500;
        this.picW700 = picW700;
        this.width = width;
        this.height = height;
        this.listenCount = listenCount;
        this.collectCount = collectCount;
        this.tag = tag;
        this.desc = desc;
        this.webUrl = webUrl;
    }

    @Generated(hash = 1850572281)
    public SheetDetail() {
    }

    public String getSheetId() {
        return sheetId;
    }

    public String getTitle() {
        return title;
    }

    public String getPic300() {
        return pic300;
    }

    public String getPic500() {
        return pic500;
    }

    public String getPicW700() {
        return picW700;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getListenCount() {
        return listenCount;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public String getTag() {
        return tag;
    }

    public String getDesc() {
        return desc;
    }

    public String getWebUrl() {
        return webUrl;
    }

    /**
     * 生成歌单唯一标识，用于播放队列的更换
     * @return
     */
    public String generateSheetUniqueTag(){
        return sheetId + "&" + title;
    }

    public void reverseSongs(){
        if(!hasReverse && songs != null){
            Collections.reverse(songs);
            hasReverse = true;
        }
    }

    public static SheetDetail fetchData(String sheetId){
        SheetDetail sheetDetail = new SheetDetailFetcher(sheetId).fetchData();
        if(sheetDetail != null){
//            下面getSongs()一直会报错，提示miss type
//            List<MusicEntity> songs = sheetDetail.getSongs();
//            for (MusicEntity song : songs) {
//                song.setNetMusic(true);
//            }
            for (MusicEntity song : sheetDetail.songs) {
                song.setNetMusic(true);
            }
        }else{// 如果网络上没有，则尝试获取本地的
            sheetDetail = ModuleManager.getDbModule().getUserDb().getSheet(sheetId);
        }
        return sheetDetail;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPic300(String pic300) {
        this.pic300 = pic300;
    }

    public void setPic500(String pic500) {
        this.pic500 = pic500;
    }

    public void setPicW700(String picW700) {
        this.picW700 = picW700;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setListenCount(int listenCount) {
        this.listenCount = listenCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public void recoverFlag(){
        this.hasReverse = false;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 180442258)
    public List<MusicEntity> getSongs() {
        if (songs == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MusicEntityDao targetDao = daoSession.getMusicEntityDao();
            List<MusicEntity> songsNew = targetDao._querySheetDetail_Songs(id);
            synchronized (this) {
                if (songs == null) {
                    songs = songsNew;
                }
            }
        }
        return songs;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 432021166)
    public synchronized void resetSongs() {
        songs = null;
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
    @Generated(hash = 1807446258)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSheetDetailDao() : null;
    }

}
