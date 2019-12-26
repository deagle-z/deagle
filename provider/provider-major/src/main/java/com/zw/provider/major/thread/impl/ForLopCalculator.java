package com.zw.provider.major.thread.impl;

import com.zw.provider.major.thread.Calculator;


/**
  * 使用最普通的遍历方法完成
  * @date 2019/12/26
  * @author zw
*/
public class ForLopCalculator implements Calculator {


    @Override
    public long sumUp(long[] numbers) {
        Long result=0L;
        for (Long number : numbers) {
            result += number;
        }
        return result;
    }
}
