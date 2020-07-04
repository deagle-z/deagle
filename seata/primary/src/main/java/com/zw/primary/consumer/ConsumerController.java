package com.zw.primary.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zw.api.order.OrderService;
import com.zw.api.storage.StorageService;
import com.zw.response.R;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
@Slf4j
public class ConsumerController {
    @Reference(version = "1.0.0", check = false)
    private OrderService orderService;

    @Reference(version = "1.0.0", check = false)
    private StorageService storageService;

    @GetMapping("/test")
    @GlobalTransactional(name = "dubbo-order", timeoutMills = 300000)
    public String consumerTest() {
        final R order = orderService.addOrder("测试", null);
        log.info(order.getMsg());
        final R storage = storageService.minusOne("测试");
        log.info(storage.getMsg());
        return "success";
    }

//    public static void main(String[] args) {
//        String pwd = new BCryptPasswordEncoder().encode("123456");
//        System.out.println(pwd);
//    }
}
