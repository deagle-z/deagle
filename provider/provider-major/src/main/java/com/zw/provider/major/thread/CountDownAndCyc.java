package com.zw.provider.major.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountDownAndCyc {


    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
//        CountDownLatch latch = new CountDownLatch(5);
//        for (int i = 0; i < 5; i++) {
//            new Thread(new readNum(i, latch)).start();
//        }
//        latch.await();
//        System.out.println("主线程执行");

        CyclicBarrier cyc = new CyclicBarrier(6);

        for (int i = 0; i < 5; i++) {
            new Thread(new MyRunnale(cyc, i + "")).start();
        }
        cyc.await();
        System.out.println("主线程结束");
    }

    static class readNum implements Runnable {
        private int id;
        private CountDownLatch latch;

        public readNum(int id, CountDownLatch latch) {
            this.id = id;
            this.latch = latch;
        }

        @Override
        public void run() {
            Lock lock = new ReentrantLock();
            lock.lock();
            try {
                System.out.println("id:" + id);
                System.out.println("线程组任务" + id + "结束，其他任务继续");
            } catch (Exception e) {
                System.out.println(e.toString());
            } finally {
                lock.unlock();
                latch.countDown();
            }
        }
    }

    static class MyRunnale implements Runnable {
        private CyclicBarrier cyclicBarrier;
        private String mark;

        public MyRunnale(CyclicBarrier cyclicBarrier, String mark) {
            super();
            this.cyclicBarrier = cyclicBarrier;
            this.mark = mark;
        }

        @Override
        public void run() {
            System.out.println(mark + "进入线程,线程阻塞中...");
            Lock lock = new ReentrantLock();
            lock.lock();
            try {
                // barrier的await方法，在所有参与者都已经在此 barrier 上调用 await 方法之前，将一直等待。
                System.out.println(mark);
//                cyclicBarrier.await();
                Thread.sleep(200);//为了看到更好的效果，线程阻塞两秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            System.out.println(mark + "线程阻塞结束,继续执行...");
        }
    }


}
