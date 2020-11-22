package com.jante.music.domain;

import java.io.Serializable;
import java.util.Date;

public class Collect implements Serializable {

    //主键
    private Integer id;
    //用户ID
    private Integer userId;
    //收藏类型 0：歌曲 1：歌单
    private Byte type;
    //歌曲id
    private Integer songId;
    //歌单id
    private Integer songListId;
    //创建时间
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public Integer getSongListId() {
        return songListId;
    }

    public void setSongListId(Integer songListId) {
        this.songListId = songListId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
