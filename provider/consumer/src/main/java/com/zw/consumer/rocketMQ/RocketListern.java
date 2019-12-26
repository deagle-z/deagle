package com.zw.consumer.rocketMQ;


import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;

@RocketMQMessageListener(topic = "test-topic-2",consumerGroup = "my-consumer_test-topic-2")
public class RocketListern  implements RocketMQListener {


    @Override
    public void onMessage(Object o) {
        System.out.println(o);
    }
}
