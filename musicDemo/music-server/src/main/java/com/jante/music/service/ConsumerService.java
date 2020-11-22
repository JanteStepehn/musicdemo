package com.jante.music.service;

import com.jante.music.domain.Consumer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConsumerService {
    /**
     * 增加
     */
    public boolean insert(Consumer consumer);

    /**
     * 修改
     */
    public boolean update(Consumer consumer);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据主键查询
     */
    public Consumer selectById(Integer id);

    /**
     * 查询所有用户
     */
    public List<Consumer> allConsumer();

    /**
     * 验证密码
     * @param userName
     * @param passWord
     * @return
     */
    public boolean verifyPassWord(@Param("username") String userName, @Param("password") String passWord);

    /**
     * 根据用户名查询
     * @param userName
     * @return
     */
    public Consumer selectByName(String userName);

}
