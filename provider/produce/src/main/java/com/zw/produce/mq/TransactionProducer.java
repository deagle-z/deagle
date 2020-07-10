package com.zw.produce.mq;

import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zw562
 */

public class TransactionProducer {
    public static void main(String[] args) {
        final TransactionListener listener = new TransactionListenerImpl();
        TransactionMQProducer producer = new TransactionMQProducer("transaction_msg");
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), r -> {
            Thread thread = new Thread(r);
            thread.setName("client_transaction_msg_check_thread");
            return thread;
        });
        producer.setExecutorService(threadPoolExecutor);
        producer.setTransactionListener(listener);
        producer.setNamesrvAddr("119.3.126.204:9876");
        try {
            producer.start();
            String[] tags = new String[] {"TagA", "TagB", "TagC", "TagD", "TagE"};
            for (int i = 0; i < 10 ; i++) {
                final Message message = new Message("TopicTest", tags[i % tags.length], "key" + i,
                        ("Hello RocketMQ" + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
                );
                final TransactionSendResult res = producer.sendMessageInTransaction(message, null);
                System.out.printf("%s%n",res);
            }
        } catch (Exception e) {
            System.out.println(e);
        }finally {
            producer.shutdown();
        }

    }
}
