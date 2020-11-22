package com.jante.music.service.impl;

import com.jante.music.dao.ListSongMapper;
import com.jante.music.domain.ListSong;
import com.jante.music.service.ListSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListSongServiceImpl implements ListSongService {
    @Autowired
    private ListSongMapper listSongMapper;

    /**
     * 增加
     *
     * @param listSong
     */
    @Override
    public boolean insert(ListSong listSong) {
        return listSongMapper.insert(listSong)>0;
    }

    /**
     * 修改
     *
     * @param listSong
     */
    @Override
    public boolean update(ListSong listSong) {
        return listSongMapper.update(listSong)>0;
    }

    /**
     * 删除
     *
     * @param songId
     *  @param songListId
     */
    @Override
    public boolean delete(Integer songId,Integer songListId) {
        return listSongMapper.delete(songId,songListId)>0;
    }

    /**
     * 根据主键查询
     *
     * @param id
     */
    @Override
    public ListSong selectById(Integer id) {
        return listSongMapper.selectById(id);
    }

    /**
     * 查询所有歌单歌曲
     */
    @Override
    public List<ListSong> allListSong() {
        return listSongMapper.allListSong();
    }

    /**
     * 根据歌单id查询查询所有歌曲
     *
     * @param songListId
     */
    @Override
    public List<ListSong> selectBySongListId(Integer songListId) {
        return listSongMapper.selectBySongListId(songListId);
    }
}
