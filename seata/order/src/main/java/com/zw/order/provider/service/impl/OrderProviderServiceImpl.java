package com.zw.order.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zw.api.order.OrderService;
import com.zw.exception.BusinessException;
import com.zw.order.business.entity.OrderEntity;
import com.zw.response.R;
import org.springframework.beans.factory.annotation.Autowired;

/**
  * dubbo test
  * @date 2020/3/17
  * @author zw
*/
@Service(version = "1.0.0",group = "deagle",interfaceName = "OrderProvider",timeout = 30000)
public class OrderProviderServiceImpl implements OrderService {

    @Autowired
    private com.zw.order.business.service.OrderService orderService;

    @Override
    public R addOrder(String code,Integer count){
        if (count == null) {
            throw new BusinessException("count 不可为空");
        }
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCommodityCode(code);
        orderEntity.setCount(count);
        orderService.save(orderEntity);
        return R.success("order服务success");
    }
}
