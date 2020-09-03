package com.zw.provider.major;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;



/**
  * 主要测试client
  * @date 2019/12/24
  * @author zw
*/
@SpringBootApplication
@ComponentScan("com.zw")
@EnableAspectJAutoProxy
public class ProviderMajorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderMajorApplication.class, args);
    }

}
