package com.zw.order.business.service;

import com.zw.order.business.entity.Order;

public interface OrderService {

    /**
     * 创建订单
     */
    Order create(String userId, String commodityCode, int orderCount);
}
