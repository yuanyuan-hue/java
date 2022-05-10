package com.qdc.demoeurekazuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class DemoEurekaZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoEurekaZuulApplication.class, args);
    }

}
