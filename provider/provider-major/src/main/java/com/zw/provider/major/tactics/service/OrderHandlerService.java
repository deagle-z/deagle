package com.zw.provider.major.tactics.service;


import com.zw.provider.major.tactics.entity.Order;

public interface OrderHandlerService {
    void handle(Order order);
}
