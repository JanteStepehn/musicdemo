package com.jante.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.jante.music.domain.Collect;
import com.jante.music.service.CollectService;
import com.jante.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/collect")
public class CollectController {
    @Autowired
    private CollectService collectService;

    /**
     * 添加
     */
    @RequestMapping("/add")
    public Object addCollect(Collect collect){
        JSONObject jsonObject = new JSONObject();

        Random random = new Random();
        int id = random.nextInt(1000);
        id = id*2+3;
        collect.setId(id);

        boolean flag = collectService.insert(collect);

        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"收藏成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"收藏失败");
        return jsonObject;
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public Object deleteCollect(HttpServletRequest request){
        String id = request.getParameter("id");
        return collectService.delete(Integer.parseInt(id));
    }

    /**
     * 删除 根据id
     */
    @RequestMapping("/deleteByTwoId")
    public Object deleteCollectByTwoId(Integer userId,Integer songId){
        return collectService.deleteByUserIdSongId(userId,songId);
    }


    /**
     * 查询所有
     */
    @RequestMapping("/allCollect")
    public List<Collect> allCollect() {
        return collectService.allCollect();
    }

    /**
     * 查询某个用户的所有收藏
     *
     * @param userId
     */
    @RequestMapping("/selectBySongId/{userId}")
    public List<Collect> selectByName(@PathVariable("userId") Integer userId) {
        return collectService.selectByUserId(userId);
    }
    @RequestMapping("/existCollect")
    public Object existCollect(Integer userId,Integer songId){
        return collectService.selectExist(userId,songId);
    }

}
