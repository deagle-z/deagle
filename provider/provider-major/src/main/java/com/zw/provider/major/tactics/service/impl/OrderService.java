package com.zw.provider.major.tactics.service.impl;


import com.zw.annotation.OrderHandlerType;
import com.zw.annotation.impl.OrderHandlerTypeImpl;
import com.zw.provider.major.tactics.entity.Order;
import com.zw.provider.major.tactics.service.OrderHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private Map<OrderHandlerType, OrderHandlerService> orderHandlerServiceMap;

    @Autowired
    public void setOrderHandlerServiceMap(List<OrderHandlerService> orderHandlerServices) {

        //注入各种类型的订单处理类
        orderHandlerServiceMap = orderHandlerServices.stream().collect(
                Collectors.toMap(orderHandler -> Objects.requireNonNull(AnnotationUtils.findAnnotation(orderHandler.getClass(), OrderHandlerType.class)),
                        v -> v, (v1, v2) -> v1));


    }

    public void orderService(Order order) {
        // ...一些前置处理

        // 通过订单来源 订单支付方式确定对应的handler
        OrderHandlerTypeImpl orderType = new OrderHandlerTypeImpl(order.getSource(),order.getPayMethod());
        OrderHandlerService orderHandler = orderHandlerServiceMap.get(orderType);
        orderHandler.handle(order);

        // ...一些后置处理
    }
}
