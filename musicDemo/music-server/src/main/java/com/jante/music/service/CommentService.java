package com.jante.music.service;

import com.jante.music.domain.Comment;

import java.util.List;

public interface CommentService {

    /**
     * 增加
     */
    public boolean insert(Comment comment);

    /**
     * 修改
     */
    public boolean update(Comment comment);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据主键查询
     */
    public Comment selectById(Integer id);

    /**
     * 查询所有评论
     */
    public List<Comment> allComment();

    /**
     * 查询某歌曲的所有评论
     */
    public List<Comment> selectBySongId(Integer SongId);

    /**
     * 查询某歌单的所有评论
     */
    public List<Comment> selectBySongListId(Integer songListId);
}
