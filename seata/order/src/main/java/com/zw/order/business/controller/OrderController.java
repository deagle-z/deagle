
package com.zw.order.business.controller;

import com.zw.order.business.service.OrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/orders")
@Api(value = "", tags = "", description = "")
public class OrderController {

    @Autowired
    private OrderService orderService;



}
