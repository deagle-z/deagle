package com.zw.provider.major.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 测试config 热更新
 *
 * @author zw
 * @date 2019/12/18
 */

@RestController
@RequestMapping("/refresh")
@RefreshScope
public class DemoController {

    @Value("${customer}")
    private String customer;

    @GetMapping("/test")
    public String test() {
        return customer;
    }
}
