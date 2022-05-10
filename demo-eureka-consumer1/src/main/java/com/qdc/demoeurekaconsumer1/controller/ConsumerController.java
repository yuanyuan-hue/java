package com.qdc.demoeurekaconsumer1.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sun.tracing.dtrace.ArgsAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {
//    依赖restTemplate对象
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @GetMapping("/sayHi")
//    没有响应，就执行fallbackMethod方法
    @HystrixCommand(fallbackMethod = "sayHiFallback",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "30000")
    })
    public String hello(@RequestParam(value = "sleep_seconds")int sleep_seconds)throws InterruptedException{
        ServiceInstance serviceInstance=loadBalancerClient.choose("eureka-provider1");
        System.out.println("端口号："+serviceInstance.getPort());
        String url="http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+
                "/user/sayHi?sleep_seconds="+sleep_seconds;
        System.out.println(url);
        RestTemplate restTemplate=new RestTemplate();
        return  restTemplate.getForObject(url,String.class);
    }
    public String sayHiFallback(int sleep_seconds){
        return "服务User暂时无法响应，请稍后……";
    }


    //    测试负载均衡，server相互注册以后，访问http://localhost:7003/port
    @RequestMapping("/port")
    public String testport(){
        return restTemplate.getForObject("http://EUREKA-PROVIDER1/port",String.class);
    }



    @RequestMapping("/users")
    public String testalluser(){
        return  restTemplate.getForObject("http://EUREKA-PROVIDER1/user/userall",String.class);
    }

    @RequestMapping("/details/{userid}")
    public String testgetUserById(@PathVariable(value = "userid")String id){
        return restTemplate.getForObject("http://EUREKA-PROVIDER1/user/details?userid="+id,String.class);
    }

    //    @RequestMapping("/addUser")
//    @ResponseBody
//    public ResponseEntity<String> testaddUser(@ResponseBody User user){
//        return restTemplate.postForEntity("http://EUREKA-PROVIDER1/user/add",String.class);
//    }
}
