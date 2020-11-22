package com.jante.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.jante.music.domain.Singer;
import com.jante.music.service.AdminService;
import com.jante.music.service.SingerService;
import com.jante.music.utils.Consts;
import com.jante.music.utils.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/singer")
public class SingerController {
    @Autowired
    private SingerService singerService;

    /**
     * 添加歌手
     */
    @RequestMapping("/add")
    public Object addSinger(String name,String sex,String pic,String birth,String location,String introduction){
        JSONObject jsonObject = new JSONObject();
        Singer singer = new Singer();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate = dateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Random random = new Random();
        int id = random.nextInt(1000);
        id = id*2+3;
        singer.setId(id);
        singer.setName(name);
        singer.setSex(new Byte(sex));
        singer.setBirth(birthDate);
        singer.setPic(pic);
        singer.setLocation(location);
        singer.setIntroduction(introduction);

        boolean flag = singerService.insert(singer);

        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"添加成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"添加失败");
        return jsonObject;
    }

    /**
     * 修改歌手
     */
    @RequestMapping("/update")
    public Object updateSinger(String id,String name,String sex,String birth,String location,String introduction){
        JSONObject jsonObject = new JSONObject();
        Singer singer = new Singer();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate = dateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        singer.setId(Integer.parseInt(id));
        singer.setName(name);
        singer.setSex(new Byte(sex));
        singer.setBirth(birthDate);
        singer.setLocation(location);
        singer.setIntroduction(introduction);
        boolean flag = singerService.update(singer);

        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改失败");
        return jsonObject;
    }

    /**
     * 删除歌手
     */
    @RequestMapping("/delete")
    public Object deleteSinger(HttpServletRequest request){
        String id = request.getParameter("id");
        return singerService.delete(Integer.parseInt(id));
    }

    /**
     * 根据主键查询
     *
     * @param id
     */
    @RequestMapping("/selectById")
    public Singer selectById(String id) {
        return singerService.selectById(Integer.parseInt(id));
    }

    /**
     * 查询所有歌手
     */
    @RequestMapping("/allSinger")
    public List<Singer> allSinger() {
        return singerService.allSinger();
    }

    /**
     * 根据歌手名字模糊查询
     *
     * @param name
     */
    @RequestMapping("/selectByName")
    public List<Singer> selectByName(String name) {
        return singerService.selectByName("%"+name+"%");
    }

    /**
     * 根据性别查询
     *
     * @param sex
     */
    @RequestMapping("/selectBySex/{sex}")
    public List<Singer> selectBySex(@PathVariable("sex") String sex) {
        return singerService.selectBySex(Integer.parseInt(sex));
    }

    /**
     * 更新歌手图片
     */
    @RequestMapping("/updateSingerPic")
    public Object updateSingerPic(MultipartFile file, int id){
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
                +System.getProperty("file.separator")+"singerPic";
        //文件路径不存在 则新建
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        //实际地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/img/singerPic/"+fileName;
        try {
            file.transferTo(dest);
            Singer singer = new Singer();
            singer.setId(id);
            singer.setPic(storeAvatorPath);
            boolean flag = singerService.update(singer);
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
}
