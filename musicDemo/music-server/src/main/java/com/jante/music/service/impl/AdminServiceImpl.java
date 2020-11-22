package com.jante.music.service.impl;

import com.jante.music.dao.AdminMapper;
import com.jante.music.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * admin service实现类
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 验证密码是否正确
     *
     * @param name
     * @param password
     * @return
     */
    @Override
    public boolean verifyPassword(String name, String password) {
        return adminMapper.verifyPassword(name,password)>0;
    }
}
