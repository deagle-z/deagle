package com.zw.produce.controller;

import com.zw.response.R;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zw562
 */
@RestController
@RequestMapping("/rocket")
public class RocketMQController {
    @Resource
    RocketMQTemplate mqTemplate;

    public static Integer NUM = 2;

    /**
     * 发送同步消息,多用于重要的消息通知,短信通知
     *
     * @author zw
     * @date 2020/7/8
     */
    @GetMapping("/syncProducer")
    private R<SendResult> syncProducer(){
        DefaultMQProducer producer = new DefaultMQProducer("degale_sync_group_"+NUM);
        producer.setNamesrvAddr("119.3.126.204:9876");
        try {
            producer.start();
            Message msg = new Message("TopicTest" ,
                    "TagA" ,
                    ("Hello RocketMQ " + NUM).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            final SendResult send = producer.send(msg);
            System.out.println(send.toString());
            return R.success(send);
        } catch (Exception e) {
            System.out.println("发送失败"+e);
            throw new RuntimeException("发送失败",e);
        }finally {
            System.out.println("NUM:"+NUM);
            NUM++;
            producer.shutdown();
        }
    }

    /**
     * 异步发送消息
     *
     * @author zw
     * @date 2020/7/8
     */
    @GetMapping("/asyncProducer")
    private R<String> asyncProducer(){
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr("119.3.126.204:9876");
        try {
            producer.start();
            producer.setRetryTimesWhenSendAsyncFailed(1);
            Message msg = new Message("TopicTest",
                    "TagB",("Hello RocketMQ.this is AsyncMsg"+NUM).getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n", NUM,
                            sendResult.toString());
                }

                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", NUM, e);
                    e.printStackTrace();
                }
            });
        return R.success("发送成功");
        } catch (Exception e) {
            throw new RuntimeException("发送失败",e);
        }finally {
            NUM++;
            producer.shutdown();
        }
    }


    /**
     * 单向发送消息
     *
     * @author zw
     * @date 2020/7/8
     */
    private R<String> oneWayProducer(){
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr("119.3.126.204:9876");
        try {
            producer.start();
            Message msg = new Message("TopicTest" ,
                    "TagA" ,
                    ("Hello RocketMQ " + NUM).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            // 发送单向消息，没有任何返回结果
            producer.sendOneway(msg);
            return R.success("发送失败");
        } catch (Exception e) {
            throw new RuntimeException();
        }finally {
            producer.shutdown();
        }
    }



}
