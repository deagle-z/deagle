package com.zw.consumer.rocketMQ;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.springframework.web.bind.annotation.RestController;

/**
  * rabitMQ消费测试
  * @date 2019/12/24
  * @author zw
*/
@RestController
public class Consumer {

    public static Integer NUM = 2;

    public static  void main(String[] args){
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("degale_sync_group_"+NUM);
        consumer.setNamesrvAddr("119.3.126.204:9876");
        try {
            consumer.subscribe("TopicTest", "*");
            consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), list);
                System.out.println();
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
            System.out.printf("Consumer start.%n");
        } catch (Exception e) {
            throw new RuntimeException("发送失败",e);
        }finally {
            System.out.printf("%6d num %n",NUM);
            NUM++;
        }

    }

}
