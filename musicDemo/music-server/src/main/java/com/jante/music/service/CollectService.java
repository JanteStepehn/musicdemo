package com.jante.music.service;

import com.jante.music.domain.Collect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectService {

    /**
     * 增加
     */
    public boolean insert(Collect collect);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据用户id歌曲id删除
     */
    public boolean deleteByUserIdSongId(Integer userId,Integer songId);


    /**
     * 查询所有
     */
    public List<Collect> allCollect();

    /**
     * 查询某个用户的所有收藏
     */
    public List<Collect> selectByUserId(Integer userId);

    /**
     * 查询某个用户是否已经收藏了某个歌曲
     */
    public boolean selectExist(Integer userId, Integer songId);
}
