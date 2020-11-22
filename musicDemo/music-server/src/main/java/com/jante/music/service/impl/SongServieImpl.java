package com.jante.music.service.impl;

import com.jante.music.dao.SongMapper;
import com.jante.music.domain.Song;
import com.jante.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SongServieImpl implements SongService {
    @Autowired
    private SongMapper songMapper;

    /**
     * 增加
     *
     * @param song
     */
    @Override
    public boolean insert(Song song) {
        return songMapper.insert(song)>0;
    }

    /**
     * 修改
     *
     * @param song
     */
    @Override
    public boolean update(Song song) {
        return songMapper.update(song)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return songMapper.delete(id)>0;
    }

    /**
     * 根据主键查询
     *
     * @param id
     */
    @Override
    public Song selectById(Integer id) {
        return songMapper.selectById(id);
    }

    /**
     * 查询所有歌曲
     */
    @Override
    public List<Song> allSong() {
        return songMapper.allSong();
    }

    /**
     * 根据歌名查询
     *
     * @param name
     */
    @Override
    public List<Song> selectByName(String name) {
        return songMapper.selectByName(name);
    }

    /**
     * 根据歌名模糊查询
     *
     * @param name
     */
    @Override
    public List<Song> selectByLikeName(String name) {
        return songMapper.selectByLikeName("%"+name+"%");
    }

    /**
     * 根据歌手id查询
     *
     * @param SingerId
     */
    @Override
    public List<Song> selectBySingerId(Integer SingerId) {
        return songMapper.selectBySingerId(SingerId);
    }
}
