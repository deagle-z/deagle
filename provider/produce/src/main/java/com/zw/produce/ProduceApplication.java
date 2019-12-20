package com.zw.produce;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class ProduceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProduceApplication.class, args);
    }

}
