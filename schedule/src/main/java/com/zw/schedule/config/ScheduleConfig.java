package com.zw.schedule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 配置核心线程池类
 *
 * @author zw562
 */
@Configuration
public class ScheduleConfig {


    @Bean
    public TaskScheduler taskScheduler(){
        final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        //定时任务获取的核心线程数
        taskScheduler.setPoolSize(4);
        taskScheduler.setRemoveOnCancelPolicy(true);
        taskScheduler.setThreadNamePrefix("TaskSchedulerThreadPool-");
        return taskScheduler;
    }
}
