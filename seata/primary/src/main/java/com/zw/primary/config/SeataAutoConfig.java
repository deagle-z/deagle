package com.zw.primary.config;

import io.seata.spring.annotation.GlobalTransactionScanner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeataAutoConfig {
    public GlobalTransactionScanner globalTransactionScanner(){
        return new GlobalTransactionScanner("dubbo-order", "dubbo_tx_service");
    }
}
