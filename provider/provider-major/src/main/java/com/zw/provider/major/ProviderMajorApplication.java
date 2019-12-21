package com.zw.provider.major;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ComponentScan("com.zw")
@EnableAspectJAutoProxy
public class ProviderMajorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderMajorApplication.class, args);
    }

}
