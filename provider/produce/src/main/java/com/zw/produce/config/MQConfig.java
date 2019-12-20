package com.zw.produce.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 初始化一些队列 交换机
 *
 * @author zw
 * @date 2019/12/20
 */

@Configuration
public class MQConfig {

    @Bean
    public Queue queueMessages() {
        return new Queue("deagle.topic.message");
    }


    @Bean
    TopicExchange topicExchange() {
//        return new TopicExchange("topicExchange");
        return (TopicExchange)ExchangeBuilder.topicExchange("topicExchange").durable(true).build();
    }

    /**
     * topic msg
     *
     * @param queueMessages
     * @param topicExchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessage(Queue queueMessages, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueMessages).to(topicExchange).with("deagle.topic.#");
    }

    @Bean
    public Queue queueMsg() {
        return new Queue("deagle.direct.msg");
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    /**
     * direct msg
     *
     * @param queueMsg
     * @param directExchange
     * @return
     */
    @Bean
    Binding bindingExchangeMsg(Queue queueMsg, DirectExchange directExchange) {
        return BindingBuilder.bind(queueMsg).to(directExchange).with("deagle.direct.msg");
    }





}
