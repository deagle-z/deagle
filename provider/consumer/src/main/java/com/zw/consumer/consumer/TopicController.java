package com.zw.consumer.consumer;


import org.springframework.stereotype.Component;


/**
 * topic模式测试
 *
 * @author zw
 * @date 2019/12/20
 */
@Component
//@RabbitListener(queues = "zw.msg")
public class TopicController {

//    @RabbitHandler
//    public void topConsumer(String message) {
//        System.out.println("zw：" + message);
//    }
}
