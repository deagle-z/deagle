package com.zw.major;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 主要 代码模块
 * @author zw
 * @date 2019/12/14
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class MajorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MajorApplication.class, args);
    }

}
