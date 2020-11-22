package com.jante.music;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 热更新，热加载
 * 1、Ctrl+shift+A --> Registry... 找到compiler.....running 勾选
 * 2.Ctrl+F9
 */

@SpringBootApplication
@MapperScan("com.jante.music.dao")
public class MusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicApplication.class, args);
    }

}
