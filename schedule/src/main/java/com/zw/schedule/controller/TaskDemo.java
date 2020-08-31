package com.zw.schedule.controller;

import com.zw.util.DateUtil;
import org.springframework.stereotype.Component;

@Component
public class TaskDemo {


    public void doSomething(){
        System.out.println(DateUtil.getCurrentTime()+"11111111111111");
    }
}
