package com.zw.produce.controller;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 生产者测试
 *
 * @author zw
 * @date 2019/12/19
 */
@RestController
@RequestMapping("/produce")
public class ProduceController {
    public static Integer count = 0;
    @Resource
    private RabbitTemplate template;


    @RequestMapping("/test")
    public void produce() {
//        fanout  direct topic headers
        for (int i = 0; i < 10; i++) {
            String context = "hello" + new Date() + i;
            template.convertAndSend("hello", context);
            System.out.println("sender:" + "hello" + i);
        }
    }

    /**
     * topic 模式测试 根据binding_key 绑定不同的队列
     * topic 的模糊匹配的两种方式 *一个字符 # 0-多个字符
     *
     * @date 2019/12/20
     */
    @GetMapping("/topic")
    public void topicProduce() {
        count++;
        String msg = "topic message " + count;
        this.template.convertAndSend("topicExchange", "deagle.topic.messages", msg);
        count++;
        String msg2 = "direct mesaages!" + count;
        this.template.convertAndSend("directExchange", "deagle.direct.msg", msg2);
    }
}
