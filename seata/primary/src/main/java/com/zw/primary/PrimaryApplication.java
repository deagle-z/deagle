package com.zw.primary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PrimaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrimaryApplication.class, args);
    }

}
