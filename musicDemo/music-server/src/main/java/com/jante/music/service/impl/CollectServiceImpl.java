package com.jante.music.service.impl;

import com.jante.music.dao.CollectMapper;
import com.jante.music.domain.Collect;
import com.jante.music.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    private CollectMapper collectMapper;
    /**
     * 增加
     *
     * @param collect
     */
    @Override
    public boolean insert(Collect collect) {
        return collectMapper.insert(collect)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return collectMapper.delete(id)>0;
    }

    /**
     * 查询所有
     */
    @Override
    public List<Collect> allCollect() {
        return collectMapper.allCollect();
    }

    /**
     * 查询某个用户的所有收藏
     *
     * @param userId
     */
    @Override
    public List<Collect> selectByUserId(Integer userId) {
        return collectMapper.selectByUserId(userId);
    }

    /**
     * 查询某个用户是否已经收藏了某个歌曲
     *
     * @param userId
     * @param songId
     */
    @Override
    public boolean selectExist(Integer userId, Integer songId) {
        return collectMapper.selectExist(userId,songId) != null;
    }

    /**
     * 根据用户id歌曲id删除
     *
     * @param userId
     * @param songId
     */
    @Override
    public boolean deleteByUserIdSongId(Integer userId, Integer songId) {
        return collectMapper.deleteByUserIdSongId(userId,songId)>0;
    }
}
