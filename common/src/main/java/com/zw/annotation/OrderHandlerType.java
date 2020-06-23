package com.zw.annotation;


import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * 策略模式 注解
 * 用于表示何种来源的订单
 * @author zw562
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface OrderHandlerType {
    String source();

    String payMethod();
}
