package com.jante.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.jante.music.dao.RankMapper;
import com.jante.music.domain.Rank;
import com.jante.music.service.RankService;
import com.jante.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/rank")
public class RankController {
    @Autowired
    private RankService rankService;

    /**
     * 增加
     * @return
     */
    @RequestMapping("add")
    public Object add(Rank rank){
        JSONObject jsonObject = new JSONObject();

        Random random = new Random();
        int id = random.nextInt(1000);
        id = id*2+3;
        rank.setId(id);
        boolean flag = rankService.insert(rank);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"评价成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"评价失败");
        return jsonObject;
    }

    /**
     * 平均分
     * @return
     */
    @RequestMapping("/avg/{songListId}")
    public Object average(@PathVariable("songListId") String songListId){
        return rankService.selectBySongListId(Integer.parseInt(songListId),"平均分");
    }


}
