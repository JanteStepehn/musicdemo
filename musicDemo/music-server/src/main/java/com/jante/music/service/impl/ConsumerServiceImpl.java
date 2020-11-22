package com.jante.music.service.impl;

import com.jante.music.dao.ConsumerMapper;
import com.jante.music.domain.Consumer;
import com.jante.music.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerServiceImpl implements ConsumerService {
    @Autowired
    private ConsumerMapper consumerMapper;
    /**
     * 增加
     *
     * @param consumer
     */
    @Override
    public boolean insert(Consumer consumer) {
        return consumerMapper.insert(consumer)>0;
    }

    /**
     * 修改
     *
     * @param consumer
     */
    @Override
    public boolean update(Consumer consumer) {
        return consumerMapper.update(consumer)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return consumerMapper.delete(id)>0;
    }

    /**
     * 根据主键查询
     *
     * @param id
     */
    @Override
    public Consumer selectById(Integer id) {
        return consumerMapper.selectById(id);
    }

    /**
     * 查询所有用户
     */
    @Override
    public List<Consumer> allConsumer() {
        return consumerMapper.allConsumer();
    }

    /**
     * 验证密码
     *
     * @param userName
     * @param passWord
     * @return
     */
    @Override
    public boolean verifyPassWord(String userName, String passWord) {
        return consumerMapper.verifyPassWord(userName,passWord)!=null;
    }

    /**
     * 根据用户名查询
     *
     * @param userName
     * @return
     */
    @Override
    public Consumer selectByName(String userName) {
        return consumerMapper.selectByName(userName);
    }
}
