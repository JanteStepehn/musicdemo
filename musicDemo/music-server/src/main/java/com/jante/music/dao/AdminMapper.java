package com.jante.music.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMapper {
    /**
     * 验证密码是否正确
     */
    public int verifyPassword(@Param("name") String name,@Param("password") String password);
}
