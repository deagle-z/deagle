package com.zw.provider.major.leetcode;

/**
 * leetcode 1114.
 *
 * @author zw
 * @date 2019/12/28
 */

public class PrintInOrder {
    private boolean first_finish=false;
    private boolean second_finish=false;

    public PrintInOrder() {

    }

    public void first(Runnable printFirst) throws InterruptedException {
        synchronized (this){
            printFirst.run();
            first_finish = true;
//            this.wait();
            this.notifyAll();
        }
        // printFirst.run() outputs "first". Do not change or remove this line.
    }

    public void second(Runnable printSecond) throws InterruptedException {
        synchronized (this) {
            while (!first_finish) {
                this.wait();
            }
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            first_finish=false;
//            this.wait();
            this.notifyAll();
        }

    }

    public void third(Runnable printThird) throws InterruptedException {
        synchronized (this) {
            while(!second_finish){
                this.wait();
            }
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        }

    }
}
