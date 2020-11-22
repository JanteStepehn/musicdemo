package com.jante.music.dao;

import com.jante.music.domain.ListSong;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ListSongMapper {
    /**
     * 增加
     */
    public int insert(ListSong listSong);

    /**
     * 修改
     */
    public int update(ListSong listSong);

    /**
     * 删除
     */
    public int delete(@Param("songId") Integer songId,@Param("songListId") Integer songListId);

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
