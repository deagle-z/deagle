package com.zw.provider.major.thread;


import com.zw.provider.major.thread.impl.ExecutorServiceCalculator;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.LongStream;

/**
  * 线程测试类
  * @date 2019/12/26
  * @author zw
*/
public class ThreadTest {


    public static void main(String[] args) {
        //默认非安全锁
        ReentrantLock reentrantLock = new ReentrantLock();
//        ForLopCalculator forLopCalculator = new ForLopCalculator();
        CompletableFuture completableFuture = new CompletableFuture();
        long[] longs = LongStream.rangeClosed(1, 100000).toArray();
        Instant start = Instant.now();
        Calculator calculator = new ExecutorServiceCalculator();
        long result = calculator.sumUp(longs);
        Instant end = Instant.now();
        System.out.println("耗时：" + Duration.between(start, end).toMillis() + "ms");
        System.out.println(result);
    }
}
