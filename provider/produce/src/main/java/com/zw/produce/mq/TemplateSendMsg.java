package com.zw.produce.mq;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class TemplateSendMsg {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void syncMsg(){
//        MessageBuilder.createMessage();
//        template.send();
        ;
        final SendResult sendResult = rocketMQTemplate.syncSend("TopicTest1", MessageBuilder.withPayload("Hello, World! I'm from spring message").build());

        rocketMQTemplate.asyncSend("TopicTest1", "1231231231", new SendCallback() {
            @Override
            public void onSuccess(SendResult var1) {
                System.out.printf("async onSucess SendResult=%s %n", var1);
            }

            @Override
            public void onException(Throwable var1) {
                System.out.printf("async onException Throwable=%s %n", var1);
            }

        });
    }
}
