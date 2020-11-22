package com.jante.music.dao;

import com.jante.music.domain.Collect;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectMapper {
    /**
     * 增加
     */
    public int insert(Collect collect);

    /**
     * 删除
     */
    public int delete(Integer id);

    /**
     * 根据用户id歌曲id删除
     */
    public int deleteByUserIdSongId(@Param("userId") Integer userId,@Param("songId") Integer songId);

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
    public Collect selectExist(@Param("userId") Integer userId, @Param("songId") Integer songId);
}
