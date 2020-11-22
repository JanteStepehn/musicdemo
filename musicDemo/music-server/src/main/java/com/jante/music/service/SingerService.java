package com.jante.music.service;

import com.jante.music.domain.Singer;

import java.util.List;

/**
 * singer service接口
 */
public interface SingerService {
    /**
     * 增加
     */
    public boolean insert(Singer singer);

    /**
     * 修改
     */
    public boolean update(Singer singer);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据主键查询
     */
    public Singer selectById(Integer id);

    /**
     * 查询所有歌手
     */
    public List<Singer> allSinger();

    /**
     * 根据歌手名字模糊查询
     */
    public List<Singer> selectByName(String name);

    /**
     * 根据性别查询
     */
    public List<Singer> selectBySex(Integer sex);
}
