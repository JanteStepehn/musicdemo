package com.jante.music.service.impl;

import com.jante.music.dao.CommentMapper;
import com.jante.music.domain.Comment;
import com.jante.music.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.OnError;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    /**
     * 增加
     *
     * @param comment
     */
    @Override
    public boolean insert(Comment comment) {
        return commentMapper.insert(comment)>0;
    }

    /**
     * 修改
     *
     * @param comment
     */
    @Override
    public boolean update(Comment comment) {
        return commentMapper.update(comment)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return commentMapper.delete(id)>0;
    }

    /**
     * 根据主键查询
     *
     * @param id
     */
    @Override
    public Comment selectById(Integer id) {
        return commentMapper.selectById(id);
    }

    /**
     * 查询所有评论
     */
    @Override
    public List<Comment> allComment() {
        return commentMapper.allComment();
    }

    /**
     * 查询某歌曲的所有评论
     *
     * @param SongId
     */
    @Override
    public List<Comment> selectBySongId(Integer SongId) {
        return commentMapper.selectBySongId(SongId);
    }

    /**
     * 查询某歌单的所有评论
     *
     * @param songListId
     */
    @Override
    public List<Comment> selectBySongListId(Integer songListId) {
        return commentMapper.selectBySongListId(songListId);
    }
}
