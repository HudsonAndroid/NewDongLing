package com.hudson.donglingmusic.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Hudson on 2020/3/20.
 */
@Entity
public class JoinSheetWithMusic {
    @Id(autoincrement = true)
    private Long id;

    // 代表sheet歌单的主键id
    private Long sId;

    // 代表music歌曲的主键id
    private Long mId;

    @Unique
    private String uniqueTag;


    @Generated(hash = 1701596532)
    public JoinSheetWithMusic(Long id, Long sId, Long mId, String uniqueTag) {
        this.id = id;
        this.sId = sId;
        this.mId = mId;
        this.uniqueTag = uniqueTag;
    }

    @Generated(hash = 1509937440)
    public JoinSheetWithMusic() {
    }

    public void generateUniqueTag(){
        this.uniqueTag = sId + "&" + mId;
    }

    public static String generateUniqueTag(SheetDetail sheetDetail,MusicEntity entity){
        return sheetDetail.getId() + "&" + entity.getId();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSId() {
        return this.sId;
    }

    public void setSId(Long sId) {
        this.sId = sId;
    }

    public Long getMId() {
        return this.mId;
    }

    public void setMId(Long mId) {
        this.mId = mId;
    }

    public String getUniqueTag() {
        return this.uniqueTag;
    }

    public void setUniqueTag(String uniqueTag) {
        this.uniqueTag = uniqueTag;
    }
}
