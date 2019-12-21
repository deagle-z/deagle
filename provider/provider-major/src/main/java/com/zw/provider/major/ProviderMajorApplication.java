package com.zw.provider.major;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.zw")
public class ProviderMajorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderMajorApplication.class, args);
    }

}
