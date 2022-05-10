package com.qdc.demoeurekaconsumerfeign.controller;

import com.qdc.demoeurekaconsumerfeign.service.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class UserController {
    @Autowired
    private UserClient userClient;
    @RequestMapping("/hi")
    public String hello(){
        return userClient.hello();
    }
    @RequestMapping(value = "/sayHi")
    public String hello(@RequestParam(value = "sleep_seconds")int sleep_seconds){
        return userClient.hello(sleep_seconds);
    }
}
