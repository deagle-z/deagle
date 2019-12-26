package com.zw.provider.major.thread.impl;

import com.zw.provider.major.thread.Calculator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


/**
  * 使用 线程池 executor
  * @date 2019/12/26
  * @author zw
*/
public class ExecutorServiceCalculator implements Calculator {

    private int parallism;
    private ExecutorService pool;
    @Resource
    ThreadPoolTaskExecutor executor;

    public ExecutorServiceCalculator() {
        //cpu的默认核心数
        parallism = Runtime.getRuntime().availableProcessors();
        pool = Executors.newFixedThreadPool(parallism);

    }



    
    @Override
    public long sumUp(long[] numbers) {
        List<CompletableFuture<Long>> results = new ArrayList<>();
        int part = numbers.length / parallism;
        for (int i = 0; i < parallism; i++) {
            int from = i * part;
            int to = (i == parallism - 1) ? numbers.length - 1 : (i + 1) * part - 1;
        }
        long total=0;
//        Future的get方法 是阻塞的
        for (CompletableFuture<Long> f : results) {
            try {
                total += f.get();
            } catch (Exception ignore) {
            }
        }
        return total;
    }

    public  static class SumTask implements Callable<Long>{
        private long[] numbers;
        private int from;
        private int to;

        public SumTask(long[] numbers, int from, int to) {
            this.numbers = numbers;
            this.from = from;
            this.to = to;
        }

        @Override
        public Long call() throws Exception {
            long total=0;
            for (int i = from; i <=to ; i++) {
                total += numbers[i];
            }
            return total;
        }
    }

}
