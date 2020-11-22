package com.jante.music.service;

import com.jante.music.domain.Rank;

import java.util.List;

public interface RankService {
    /**
     * 增加
     */
    public boolean insert(Rank rank);


    /**
     * 根据歌单id查询
     */
    public int selectBySongListId(Integer songListId,String type);


}
