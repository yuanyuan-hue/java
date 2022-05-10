package com.qdc.demoeurekaconsumerfeign.service;
//这个类就是熔断器
public class FeignClientFallBack implements UserClient{
    @Override
    public String hello() {
        return "";
    }

    @Override
    public String hello(int sleep_seconds) {
        return "Hi,容错保护机制已经启动啦！";
    }

}
