package com.jante.music.service.impl;

import com.jante.music.dao.SongListMapper;
import com.jante.music.domain.SongList;
import com.jante.music.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SongListServiceImpl implements SongListService {
    @Autowired
    private SongListMapper songListMapper;

    /**
     * 增加
     *
     * @param songList
     */
    @Override
    public boolean insert(SongList songList) {
        return songListMapper.insert(songList)>0;
    }

    /**
     * 修改
     *
     * @param songList
     */
    @Override
    public boolean update(SongList songList) {
        return songListMapper.update(songList)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return songListMapper.delete(id)>0;
    }

    /**
     * 根据主键查询
     *
     * @param id
     */
    @Override
    public SongList selectById(Integer id) {
        return songListMapper.selectById(id);
    }

    /**
     * 查询所有歌单
     */
    @Override
    public List<SongList> allSongList() {
        return songListMapper.allSongList();
    }

    /**
     * 根据标题模糊查询
     *
     * @param title
     */
    @Override
    public List<SongList> selectByTitle(String title) {
        return songListMapper.selectByTitle(title);
    }

    /**
     * 根据标题查询
     *
     * @param title
     */
    @Override
    public List<SongList> selectTrueByTitle(String title) {
        return songListMapper.selectTrueByTitle(title);
    }

    /**
     * 根据风格
     *
     * @param style
     */
    @Override
    public List<SongList> selectByStyle(String style) {
        return songListMapper.selectByStyle(style);
    }
}
