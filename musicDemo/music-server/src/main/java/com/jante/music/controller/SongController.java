package com.jante.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.jante.music.dao.SongMapper;
import com.jante.music.domain.Singer;
import com.jante.music.domain.Song;
import com.jante.music.service.SingerService;
import com.jante.music.service.SongService;
import com.jante.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/song")
public class SongController {
    @Autowired
    private SongService songService;

    /**
     * 添加歌曲
     * @return
     */
    @RequestMapping("/add")
    public Object addSong(Song song,MultipartFile file){
        JSONObject jsonObject = new JSONObject();

        Random random = new Random();
        int id = random.nextInt(1000);
        id = id*2+3;
        song.setId(id);
        song.setPic("/img/songPic/tubiao.jpg");

        //上传歌曲文件
        if(file.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"歌曲上传失败");
            return jsonObject;
        }
        //文件名
        String fileName = System.currentTimeMillis()+file.getOriginalFilename();
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"song";
        //文件路径不存在 则新建
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        //实际地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeUrlPath = "/song/"+fileName;
        try {
            file.transferTo(dest);
            song.setUrl(storeUrlPath);
            boolean flag = songService.insert(song);

            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"保存成功");
                jsonObject.put("url",storeUrlPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"保存失败");
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"保存失败"+e.getMessage());
        }
        return jsonObject;
    }


    /**
     * 根据歌手id查询歌曲
     */
    @RequestMapping("/singer/detail/{id}")
    public Object selectBySingerId(@PathVariable("id") String singerId){
        return songService.selectBySingerId(Integer.parseInt(singerId));
    }

    /**
     * 根据歌曲id(主键)查询歌曲
     */
    @RequestMapping("/detail/{id}")
    public Object selectBySongId(@PathVariable("id") String songId){
        return songService.selectById(Integer.parseInt(songId));
    }

    /**
     * 根据歌手-歌名查询歌曲
     */
    @RequestMapping("/detailByName/{name}")
    public Object selectBySongName(@PathVariable("name") String name){
        return songService.selectByName(name);
    }

    /**
     * 根据歌手-歌名模糊查询歌曲
     */
    @RequestMapping("/detailByLikeName/{name}")
    public Object selectBySongLikeName(@PathVariable("name") String name){
        return songService.selectByLikeName(name);
    }


    /**
     * 修改歌曲
     * @return
     */
    @RequestMapping("/update")
    public Object updateSong(Song song){
        JSONObject jsonObject = new JSONObject();
        boolean flag = songService.update(song);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改失败");
        return jsonObject;
    }

    @RequestMapping("/delete/{id}")
    public Object deleteSong(@PathVariable("id") String id){
        return songService.delete(Integer.parseInt(id));
    }

    /**
     * 更新歌曲图片
     */
    @RequestMapping("/updateSongPic")
    public Object updateSongPic(MultipartFile file, int id){
        JSONObject jsonObject = new JSONObject();
        if(file.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        //文件名
        String fileName = System.currentTimeMillis()+file.getOriginalFilename();
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"songPic";
        //文件路径不存在 则新建
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        //实际地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/img/songPic/"+fileName;
        try {
            file.transferTo(dest);
            Song song = new Song();
            song.setId(id);
            song.setPic(storeAvatorPath);
            boolean flag = songService.update(song);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("pic",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败"+e.getMessage());
        }finally {
            return jsonObject;
        }
    }

    /**
     * 更新歌曲
     */
    @RequestMapping("/updateSongUrl")
    public Object updateSongUrl(MultipartFile file, int id){
        JSONObject jsonObject = new JSONObject();
        if(file.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        //文件名
        String fileName = System.currentTimeMillis()+file.getOriginalFilename();
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"song";
        //文件路径不存在 则新建
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        //实际地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/song/"+fileName;
        try {
            file.transferTo(dest);
            Song song = new Song();
            song.setId(id);
            song.setUrl(storeAvatorPath);
            boolean flag = songService.update(song);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("url",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败"+e.getMessage());
        }finally {
            return jsonObject;
        }
    }

    /**
     * 查询所有歌曲
     * @return
     */
    @RequestMapping("/allSong")
    public Object allSong(){
        return songService.allSong();
    }
}
