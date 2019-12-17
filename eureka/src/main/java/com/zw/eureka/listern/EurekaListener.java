package com.zw.eureka.listern;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author zw
 * @description 监听eureka启动 关闭等操作
 * @date 2019/12/17
 */
@Slf4j
@Component
@SuppressWarnings("")
public class EurekaListener {

    public void listener(EurekaInstanceCanceledEvent event) {
        log.info("服务：{}|{}|挂了", event.getAppName(), event.getServerId());
    }

    public void listener(EurekaInstanceRegisteredEvent event) {
        log.info("服务：{}|{}|注册成功了", event.getInstanceInfo().getAppName(), event.getInstanceInfo().getIPAddr());
    }

    public void listener(EurekaInstanceRenewedEvent event) {
        log.info("服务：{}|{}|检测心跳", event.getInstanceInfo().getAppName(), event.getInstanceInfo().getIPAddr());
    }

    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
        //注册中心启动事件
        log.info(LocalDateTime.now()+",eureka启动");
    }

    @EventListener
    public void listen(EurekaServerStartedEvent event) {
        //Server启动
        log.info(LocalDateTime.now()+",服务启动");
    }
}
