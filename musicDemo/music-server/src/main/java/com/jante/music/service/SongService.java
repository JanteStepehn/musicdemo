package com.jante.music.service;

import com.jante.music.domain.Song;

import java.util.List;

public interface SongService  {
    /**
     * 增加
     */
    public boolean insert(Song song);

    /**
     * 修改
     */
    public boolean update(Song song);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据主键查询
     */
    public Song selectById(Integer id);

    /**
     * 查询所有歌曲
     */
    public List<Song> allSong();

    /**
     * 根据歌名查询
     */
    public List<Song> selectByName(String name);

    /**
     * 根据歌名模糊查询
     */
    public List<Song> selectByLikeName(String name);

    /**
     * 根据歌手id查询
     */
    public List<Song> selectBySingerId(Integer SingerId);
}
