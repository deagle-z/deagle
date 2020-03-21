package com.zw.primary.consumer;

import com.zw.api.order.OrderService;
import com.zw.api.storage.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    @Reference(version = "1.0.0" ,check = false)
    private OrderService orderService;

    @Reference(version = "1.0.0" ,check = false)
    private StorageService storageService;

    @GetMapping("/test")
    @GlobalTransactional
    public String consumerTest(){
        orderService.addOrder("测试", null);
        storageService.minusOne("测试");
        return "success";
    }
}
