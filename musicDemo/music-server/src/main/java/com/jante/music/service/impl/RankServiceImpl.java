package com.jante.music.service.impl;

import com.jante.music.dao.RankMapper;
import com.jante.music.domain.Rank;
import com.jante.music.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankServiceImpl implements RankService {
    @Autowired
    private RankMapper rankMapper;
    /**
     * 增加
     *
     * @param rank
     */
    @Override
    public boolean insert(Rank rank) {
        return rankMapper.insert(rank)>0;
    }

    /**
     * 根据歌单id查询
     *
     * @param songListId
     */
    @Override
    public int selectBySongListId(Integer songListId,String type) {

        int index = 0;
        List<Rank> listSelect = rankMapper.selectBySongListId(songListId);

        if(type!=null && type.equals("总分")){
            for (Rank rank : listSelect) {
                index += rank.getScore();
            }
        }

        if(type!=null && type.equals("总人数")){
            index = listSelect.size();
        }

        if(type!=null && type.equals("平均分")){
            for (Rank rank : listSelect) {
                index += rank.getScore();
            }
            index = index / listSelect.size();
        }


        return index;
    }
}
