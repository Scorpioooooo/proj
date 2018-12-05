package com.coocaa.pro.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.coocaa.pro.manage.mapper")
@EnableCaching
public class ProManageserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProManageserverApplication.class, args);
    }
}
