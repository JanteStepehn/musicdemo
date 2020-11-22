package com.jante.music.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jante.music.domain.Comment;
import com.jante.music.service.CommentService;
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
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 添加
     */
    @RequestMapping("/add")
    public Object addComment(Comment comment){
        JSONObject jsonObject = new JSONObject();

        Random random = new Random();
        int id = random.nextInt(1000);
        id = id*2+3;
        comment.setId(id);

        boolean flag = commentService.insert(comment);

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
     * 修改
     */
    @RequestMapping("/update")
    public Object updateComment(Comment comment){
        JSONObject jsonObject = new JSONObject();
        boolean flag = commentService.update(comment);

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
     * 删除
     */
    @RequestMapping("/delete")
    public Object deleteComment(HttpServletRequest request){
        String id = request.getParameter("id");
        return commentService.delete(Integer.parseInt(id));
    }

    /**
     * 根据主键查询
     *
     * @param id
     */
    @RequestMapping("/selectById")
    public Comment selectById(String id) {
        return commentService.selectById(Integer.parseInt(id));
    }

    /**
     * 查询所有
     */
    @RequestMapping("/allComment")
    public List<Comment> allComment() {
        return commentService.allComment();
    }

    /**
     * 查询歌曲下的所有评论
     *
     * @param songId
     */
    @RequestMapping("/selectBySongId/{songId}")
    public List<Comment> selectByName(@PathVariable("songId") Integer songId) {
        return commentService.selectBySongId(songId);
    }

    /**
     * 查询歌单下的所有评论
     *
     * @param songListId
     */
        @RequestMapping("/selectBySongListId/{songListId}")
    public List<Comment> selectBySex(@PathVariable("songListId") Integer songListId) {
        return commentService.selectBySongListId(songListId);
    }

    /**
     * 点赞
     *
     * @param comment
     */
    @RequestMapping("/like")
    public Object like(Comment comment) {
        JSONObject jsonObject = new JSONObject();
        boolean flag = commentService.update(comment);

        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"点赞成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"点赞失败");
        return jsonObject;
    }
}
