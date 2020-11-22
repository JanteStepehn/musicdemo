package com.jante.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.jante.music.domain.ListSong;
import com.jante.music.service.ListSongService;
import com.jante.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Random;

@RestController
@RequestMapping("/listSong")
public class ListSongController {
    @Autowired
    private ListSongService listSongService;

    /**
     * 添加歌曲
     * @return
     */
    @RequestMapping("/add")
    public Object addListSong(ListSong listSong){
        JSONObject jsonObject = new JSONObject();

        Random random = new Random();
        int id = random.nextInt(1000);
        id = id*2+3;
        listSong.setId(id);
        boolean flag = listSongService.insert(listSong);

        if(flag) {
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "保存成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"保存失败");
        return jsonObject;
    }


    /**
     * 根据歌手id查询歌曲
     */
    @RequestMapping("/detail/{id}")
    public Object selectBySongListId(@PathVariable("id") String songListId){
        return listSongService.selectBySongListId(Integer.parseInt(songListId));
    }

    /**
     * 删除
     * @param songId
     * @param songListId
     * @return
     */
    @RequestMapping("/delete")
    public Object deleteListSong(String songId,String songListId){
        return listSongService.delete(Integer.parseInt(songId),Integer.parseInt(songListId));
    }

}
