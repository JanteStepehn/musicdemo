package com.jante.music.dao;

import com.jante.music.domain.Rank;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RankMapper {
    /**
     * 增加
     */
    public int insert(Rank rank);


    /**
     * 根据歌单id查询
     */
    public List<Rank> selectBySongListId(Integer songListId);



}
