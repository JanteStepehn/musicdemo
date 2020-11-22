package com.jante.music.service.impl;

import com.jante.music.dao.AdminMapper;
import com.jante.music.dao.SingerMapper;
import com.jante.music.domain.Singer;
import com.jante.music.service.AdminService;
import com.jante.music.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *singer service实现类
 */
@Service
public class SingerServiceImpl implements SingerService {

    @Autowired
    private SingerMapper singerMapper;
    /**
     * 增加
     *
     * @param singer
     */
    @Override
    public boolean insert(Singer singer) {
        return singerMapper.insert(singer)>0;
    }

    /**
     * 修改
     *
     * @param singer
     */
    @Override
    public boolean update(Singer singer) {
        return singerMapper.update(singer)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return singerMapper.delete(id)>0;
    }

    /**
     * 根据主键查询
     *
     * @param id
     */
    @Override
    public Singer selectById(Integer id) {
        return singerMapper.selectById(id);
    }

    /**
     * 查询所有歌手
     */
    @Override
    public List<Singer> allSinger() {
        return singerMapper.allSinger();
    }

    /**
     * 根据歌手名字模糊查询
     *
     * @param name
     */
    @Override
    public List<Singer> selectByName(String name) {
        return singerMapper.selectByName(name);
    }

    /**
     * 根据性别查询
     *
     * @param sex
     */
    @Override
    public List<Singer> selectBySex(Integer sex) {
        return singerMapper.selectBySex(sex);
    }
}
