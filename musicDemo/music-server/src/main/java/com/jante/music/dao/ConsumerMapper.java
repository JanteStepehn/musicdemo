package com.jante.music.dao;

import com.jante.music.domain.Consumer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumerMapper {
    /**
     * 增加
     */
    public int insert(Consumer consumer);

    /**
     * 修改
     */
    public int update(Consumer consumer);

    /**
     * 删除
     */
    public int delete(Integer id);

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
    public Consumer verifyPassWord(@Param("username") String userName, @Param("password") String passWord);

    /**
     * 根据用户名查询
     * @param userName
     * @return
     */
    public Consumer selectByName(String userName);
}
