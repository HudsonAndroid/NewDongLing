package com.hudson.musicentitylib;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Hudson on 2019/1/1.
 */
@Entity
public class MusicEntity {
    @Id(autoincrement = true)
    private long id;
    private String songId;
    private String albumId;
    private long duration;
    private String title;
    private String artist;
    private String albumName;
    private String path;
    @Generated(hash = 1031116924)
    public MusicEntity(long id, String songId, String albumId, long duration,
            String title, String artist, String albumName, String path) {
        this.id = id;
        this.songId = songId;
        this.albumId = albumId;
        this.duration = duration;
        this.title = title;
        this.artist = artist;
        this.albumName = albumName;
        this.path = path;
    }
    @Generated(hash = 1380251324)
    public MusicEntity() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getSongId() {
        return this.songId;
    }
    public void setSongId(String songId) {
        this.songId = songId;
    }
    public String getAlbumId() {
        return this.albumId;
    }
    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }
    public long getDuration() {
        return this.duration;
    }
    public void setDuration(long duration) {
        this.duration = duration;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getArtist() {
        return this.artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public String getAlbumName() {
        return this.albumName;
    }
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
}
