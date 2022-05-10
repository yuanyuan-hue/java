package com.qdc.demotest;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoTestApplicationTests {
@Autowired
private DruidDataSource druidDataSource;
    @Test
    void contextLoads() {
        System.out.println(druidDataSource);
    }

}
