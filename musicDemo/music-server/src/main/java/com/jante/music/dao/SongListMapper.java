package com.jante.music.dao;

import com.jante.music.domain.Song;
import com.jante.music.domain.SongList;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SongListMapper {
    /**
     * 增加
     */
    public int insert(SongList songList);

    /**
     * 修改
     */
    public int update(SongList songList);

    /**
     * 删除
     */
    public int delete(Integer id);

    /**
     * 根据主键查询
     */
    public SongList selectById(Integer id);

    /**
     * 查询所有歌单
     */
    public List<SongList> allSongList();

    /**
     * 根据标题模糊查询
     */
    public List<SongList> selectByTitle(String title);

    /**
     * 根据标题查询
     */
    public List<SongList> selectTrueByTitle(String title);

    /**
     * 根据风格
     */
    public List<SongList> selectByStyle(String style);
}
