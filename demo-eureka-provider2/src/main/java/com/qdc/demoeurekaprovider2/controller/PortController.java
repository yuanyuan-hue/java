package com.qdc.demoeurekaprovider2.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortController {
    @Value("${server.port}")
    String port;

    @RequestMapping("/port")
    public String testport(){
        return "Hello,I am from"+port;
    }
}
