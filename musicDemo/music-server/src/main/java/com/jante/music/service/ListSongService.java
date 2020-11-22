package com.jante.music.service;

import com.jante.music.domain.ListSong;

import java.util.List;

public interface ListSongService {
    /**
     * 增加
     */
    public boolean insert(ListSong listSong);

    /**
     * 修改
     */
    public boolean update(ListSong listSong);

    /**
     * 删除
     */
    public boolean delete(Integer songId,Integer songListId);

    /**
     * 根据主键查询
     */
    public ListSong selectById(Integer id);

    /**
     * 查询所有歌单歌曲
     */
    public List<ListSong> allListSong();

    /**
     * 根据歌单id查询查询所有歌曲
     */
    public List<ListSong> selectBySongListId(Integer songListId);
}
