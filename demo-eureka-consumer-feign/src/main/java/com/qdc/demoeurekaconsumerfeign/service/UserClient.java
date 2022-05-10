package com.qdc.demoeurekaconsumerfeign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(name="eureka-provider1",fallback = FeignClientFallBack.class)   //指定远程调用的服务名
public interface UserClient {
    @RequestMapping("/port")    //用GetMapping标识远程调用的http的方法类型
    public String hello();

//    Feign测试容错保护机制
    @RequestMapping(value = "/user/sayHi")
    public String hello(@RequestParam(value = "sleep_seconds")int sleep_seconds);
}
