package com.zw.consumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者测试
 *
 * @author zw
 * @date 2019/12/20
 */
@Component
@Slf4j

public class ConsumerController {

    @RabbitListener(queues = "deagle.topic.message")
    public void topConsumer(String message) {
        System.out.println("topic:" + message);
    }


    @RabbitListener(queues = "deagle.direct.msg")
    public void directConsumer(String message) {
        System.out.println("direct:" + message);
    }
}
