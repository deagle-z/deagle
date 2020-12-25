package com.zw.provider.major.tactics.controller;

import com.zw.provider.major.tactics.entity.Order;
import com.zw.provider.major.tactics.service.impl.OrderService;
import com.zw.response.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 测试策略模式处理
 *
 * @author zw
 * @date 2020/12/25
 */
@RestController
@RequestMapping("/Order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/pay")
    public R<String> success(){
        orderService.pay(new Order("pc", "weChatPay", "1", new BigDecimal("1")));
        return R.success();
    }
}
