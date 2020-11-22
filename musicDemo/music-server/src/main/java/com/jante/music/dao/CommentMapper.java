package com.jante.music.dao;

import com.jante.music.domain.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {
    /**
     * 增加
     */
    public int insert(Comment comment);

    /**
     * 修改
     */
    public int update(Comment comment);

    /**
     * 删除
     */
    public int delete(Integer id);

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
