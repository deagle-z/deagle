
package com.zw.order.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zw.order.business.mapper.OrderMapper;
import com.zw.order.business.entity.OrderEntity;
import com.zw.order.business.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper,OrderEntity> implements OrderService {

}
