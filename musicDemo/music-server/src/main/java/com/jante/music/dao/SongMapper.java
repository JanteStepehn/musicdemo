package com.jante.music.dao;

import com.jante.music.domain.Song;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SongMapper {
    /**
     * 增加
     */
    public int insert(Song song);

    /**
     * 修改
     */
    public int update(Song song);

    /**
     * 删除
     */
    public int delete(Integer id);

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
