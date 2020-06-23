package com.zw.provider.major.tactics.service.impl;

import com.zw.annotation.OrderHandlerType;
import com.zw.provider.major.tactics.entity.Order;
import com.zw.provider.major.tactics.service.OrderHandlerService;

@OrderHandlerType(source = "pc",payMethod = "weChatPay")
public class PCOrderHandlerServiceImpl implements OrderHandlerService {
    @Override
    public void handle(Order order) {
        System.out.println("处理pc订单,weChatPay");
    }
}
