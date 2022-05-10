package com.qdc.demoeurekaprovider1.controller;

import com.qdc.demoeurekaprovider1.pojo.User;
import com.qdc.demoeurekaprovider1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Value("${spring.cloud.client.ip-address}")
    String ipaddr;
    @Value("${server.port}")
    int port;


//    @RestController返回前端和接收前端的数据都是json格式
//    把json格式转换成user对象
//    @RequestBody处理json格式
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public boolean addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public boolean updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public boolean deleteUser(@RequestParam(value = "username",required = true) String name){
        return userService.deleteUser(name);
    }

    @RequestMapping(value = "/details",method = RequestMethod.GET)
    public User selectUserById(@RequestParam(value = "userid",required = true) String id){
        return userService.selectUserById(id);
    }

    @RequestMapping(value = "/userall",method = RequestMethod.GET)
    public List<User> selectUserById(){
       return userService.selectAllUser();
    }

    @RequestMapping(value = "/sayHi",method = RequestMethod.GET)
    public String hello(@RequestParam(value = "sleep_seconds",required = true)int sleep_seconds)
    throws InterruptedException{
        System.out.println("休眠时间："+sleep_seconds);
//        延迟响应
        Thread.sleep(sleep_seconds*1000);
        return "Hello,我在"+ipaddr+":"+port;
    }
}
