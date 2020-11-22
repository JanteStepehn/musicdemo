package com.jante.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.jante.music.domain.SongList;
import com.jante.music.service.SongListService;
import com.jante.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/songList")
public class SongListController {
    @Autowired
    private SongListService songListService;

    /**
     * 添加歌单
     */
    @RequestMapping("/add")
    public Object addSongList(SongList songList){
        JSONObject jsonObject = new JSONObject();

        Random random = new Random();
        int id = random.nextInt(1000);
        id = id*2+3;
        songList.setId(id);

        boolean flag = songListService.insert(songList);

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
     * 修改歌单
     */
    @RequestMapping("/update")
    public Object updateSongList(SongList songList){
        JSONObject jsonObject = new JSONObject();

        boolean flag = songListService.update(songList);

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
     * 删除歌单
     */
    @RequestMapping("/delete")
    public Object deleteSongList(HttpServletRequest request){
        String id = request.getParameter("id");
        return songListService.delete(Integer.parseInt(id));
    }

    /**
     * 根据主键查询
     *
     * @param id
     */
    @RequestMapping("/selectById")
    public SongList selectById(String id) {
        return songListService.selectById(Integer.parseInt(id));
    }

    /**
     * 查询所有歌单
     */
    @RequestMapping("/allSongList")
    public List<SongList> allSongList() {
        return songListService.allSongList();
    }

    /**
     * 根据歌单标题模糊查询
     *
     * @param title
     */
    @RequestMapping("/selectByTitle/{title}")
    public List<SongList> selectByName(@PathVariable("title") String title) {
        return songListService.selectByTitle("%"+title+"%");
    }

    /**
     * 根据歌单标题精确查询
     *
     * @param title
     */
    @RequestMapping("/selectTrueByTitle")
    public List<SongList> selectBySex(String title) {
        return songListService.selectTrueByTitle(title);
    }

    /**
     * 根据歌单风格模糊查询
     *
     * @param style
     */
    @RequestMapping("/selectByStyle/{style}")
    public List<SongList> selectByStyle(@PathVariable("style") String style) {
        return songListService.selectByStyle("%"+style+"%");
    }

    /**
     * 更新歌单图片
     */
    @RequestMapping("/updateSongListPic")
    public Object updateSongListPic(MultipartFile file, int id){
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
                +System.getProperty("file.separator")+"songListPic";
        //文件路径不存在 则新建
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        //实际地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/img/songListPic/"+fileName;
        try {
            file.transferTo(dest);
            SongList songList = new SongList();
            songList.setId(id);
            songList.setPic(storeAvatorPath);
            boolean flag = songListService.update(songList);
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
