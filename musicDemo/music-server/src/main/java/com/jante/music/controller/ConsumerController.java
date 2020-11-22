package com.jante.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.jante.music.domain.Consumer;
import com.jante.music.service.ConsumerService;
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
@RequestMapping("/consumer")
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;

    /**
     * 注册
     */
    @RequestMapping("/add")
    public Object addConsumer(String userName,String passWord,String sex,String phoneNum,String email
                              ,String birth,String introduction,String location,String avator){
        JSONObject jsonObject = new JSONObject();

        if(consumerService.selectByName(userName)!=null){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"添加失败");
            return jsonObject;
        }

        Consumer consumer = new Consumer();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate = dateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Random random = new Random();
        int id = random.nextInt(1000);
        id = id*2+3;
        consumer.setId(id);
        consumer.setUsername(userName);
        consumer.setPassword(passWord);
        consumer.setSex(new Byte(sex));
        consumer.setPhoneNum(phoneNum);
        consumer.setEmail(email);
        consumer.setBirth(birthDate);
        consumer.setIntroduction(introduction);
        consumer.setLocation(location);
        consumer.setAvator(avator);

        boolean flag = consumerService.insert(consumer);

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
     * 修改歌手
     */
    @RequestMapping("/update")
    public Object updateConsumer(String id,String userName,String passWord,String sex,String phoneNum,String email
            ,String birth,String introduction,String location){
        JSONObject jsonObject = new JSONObject();

        Consumer consumer = new Consumer();

        Date birthDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            birthDate = dateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        consumer.setId(Integer.parseInt(id));
        consumer.setUsername(userName);
        consumer.setPassword(passWord);
        consumer.setSex(new Byte(sex));
        consumer.setPhoneNum(phoneNum);
        consumer.setEmail(email);
        consumer.setBirth(birthDate);
        consumer.setIntroduction(introduction);
        consumer.setLocation(location);

        boolean flag = consumerService.update(consumer);

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
     * 删除用户
     */
    @RequestMapping("/delete")
    public Object deleteConsumer(HttpServletRequest request){
        String id = request.getParameter("id");
        return consumerService.delete(Integer.parseInt(id));
    }

    /**
     * 根据主键查询
     *
     * @param id
     */
    @RequestMapping("/selectById/{id}")
    public Consumer selectById(@PathVariable("id") String id) {
        return consumerService.selectById(Integer.parseInt(id));
    }

    /**
     * 查询所有用户
     */
    @RequestMapping("/allConsumer")
    public List<Consumer> allConsumer() {
        return consumerService.allConsumer();
    }

    /**
     * 根据用户名查询
     *
     * @param userName
     */
    @RequestMapping("/selectByName")
    public Consumer selectByName(String userName) {
        return consumerService.selectByName(userName);
    }


    /**
     * 更新用户图片
     */
    @RequestMapping("/updateConsumerPic")
    public Object updateConsumerPic(MultipartFile file, int id){
        JSONObject jsonObject = new JSONObject();
        if(file.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        //文件名
        String fileName = System.currentTimeMillis()+file.getOriginalFilename();
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"avatorPic";
        //文件路径不存在 则新建
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        //实际地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/img/avatorPic/"+fileName;
        try {
            file.transferTo(dest);
            Consumer consumer = new Consumer();
            consumer.setId(id);
            consumer.setAvator(storeAvatorPath);
            boolean flag = consumerService.update(consumer);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("avator",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败"+e.getMessage());
        }finally {
            return jsonObject;
        }
    }

    /**
     * 登录
     */
    @RequestMapping("/login")
    public Object Login(String userName,String passWord){
        JSONObject jsonObject = new JSONObject();

        boolean flag = consumerService.verifyPassWord(userName,passWord);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"登录成功");
            jsonObject.put("userMsg",consumerService.selectByName(userName));
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"用户名或密码错误");
        return jsonObject;
    }
}
