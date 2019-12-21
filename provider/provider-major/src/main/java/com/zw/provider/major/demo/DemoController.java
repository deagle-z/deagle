package com.zw.provider.major.demo;

import com.zw.util.RedissonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


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

    @Resource
    private RedissonUtil redissonUtil;

    /**
     * 测试动态刷新
     *
     * @date 2019/12/21
     * @author zw
     */
    @GetMapping("/test")
    public String test() {
        return customer;
    }

    @GetMapping("/lock")
    public String lockTest() {
        String key = "111";
        boolean b = redissonUtil.tryLock(key);
        System.out.println(b);
        redissonUtil.unLock(key);
        return "success";
    }

}
