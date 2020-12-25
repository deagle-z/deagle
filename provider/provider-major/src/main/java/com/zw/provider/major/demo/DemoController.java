// package com.zw.provider.major.demo;
//
// import com.zw.annotation.Log;
// import com.zw.util.RedissonUtil;
// import org.redisson.api.RLock;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.cloud.context.config.annotation.RefreshScope;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// import javax.annotation.Resource;
//
//
// /**
//  * 测试config 热更新
//  *
//  * @author zw
//  * @date 2019/12/18
//  */
//
// @RestController
// @RequestMapping("/refresh")
// @RefreshScope
// public class DemoController {
//
//     @Value("${customer}")
//     private String customer;
//
//     @Resource
//     private RedissonUtil redissonUtil;
//
// //    @Resource
// //    private RocketMQTemplate rocketMQTemplate;
//
//     /**
//      * 测试动态刷新
//      *
//      * @date 2019/12/21
//      * @author zw
//      */
//     @GetMapping("/test")
//     public String test() {
//         return customer;
//     }
//
//     @GetMapping("/lock")
//     @Log
//     public String lockTest() {
//         String key = "1111";
//         RLock rLock = redissonUtil.tryLock(key, Long.valueOf(5));
//         System.out.println(rLock);
//         if (rLock != null) {
//             rLock.unlock();
//         }
//         return "success";
//     }
//
//
// //    @GetMapping("/RocketMQSyncTest")
// //    public String syncProducer() {
// //        DefaultMQProducer producer = new DefaultMQProducer("DefaultCluster");
// //        producer.setNamesrvAddr("192.168.80.128:9876");
// //        try {
// //            producer.start();
// //            Message msg = new Message("TopicTest", "TagA", ("Hello RocketMQ").getBytes(RemotingHelper.DEFAULT_CHARSET));
// //            final SendResult sendResult = producer.send(msg);
// //            System.out.println(sendResult);
// //            return sendResult.toString();
// //        } catch (Exception e) {
// //            e.printStackTrace();
// //            return e.toString();
// //        } finally {
// //            producer.shutdown();
// //        }
// //    }
// //
// //    @GetMapping("/RocketMQASyncTest")
// //    public String rocketMQASyncTest() {
// //        DefaultMQProducer producer = new DefaultMQProducer("DefaultCluster");
// //        producer.setNamesrvAddr("192.168.80.128:9876");
// //        try {
// //            producer.start();
// //            //异步发送失败 重试次数
// //            producer.setRetryTimesWhenSendAsyncFailed(0);
// //            Message message = new Message("TopicTest", "TagA", "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
// //            producer.send(message,
// //                    new SendCallback() {
// //                        @Override
// //                        public void onSuccess(SendResult sendResult) {
// //                            System.out.printf("%-10d OK %s %n", 1,
// //                                    sendResult.getMsgId());
// //                            System.out.println(sendResult.toString());
// //                        }
// //
// //                        @Override
// //                        public void onException(Throwable e) {
// //                            System.out.printf("%-10d Exception %s %n", 1, e);
// //                            System.out.println(e.toString());
// //                            e.printStackTrace();
// //                        }
// //                    });
// //        } catch (Exception e) {
// //            return e.toString();
// //        } finally {
// //            producer.shutdown();
// //        }
// //        return null;
// //    }
// //
// //
// //    @GetMapping("/delayTimeTest")
// //    public String delayTimeTest() {
// //        DefaultMQProducer producer = new DefaultMQProducer("DefaultCluster_1");
// //        producer.setNamesrvAddr("192.168.80.128:9876");
// //        try {
// //            producer.start();
// //            Message message = new Message("TopicTest", "TagA", "Hello world deplayTimeTest".getBytes(RemotingHelper.DEFAULT_CHARSET));
// //            message.setDelayTimeLevel(5);
// //            SendResult sendResult = producer.send(message);
// //            System.out.println(sendResult.getMessageQueue());
// //            return sendResult.toString();
// //        } catch (Exception e) {
// //            return e.getMessage();
// //        }
// //    }
// //
// //
// //    @GetMapping("/genericProduce")
// //    public void rocketTemplate(){
// //        GenericMessage genericMessage = new GenericMessage(new Object());
// //        rocketMQTemplate.send("test",  genericMessage);
// //    }
// //
//
// }
