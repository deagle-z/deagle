package com.zw.schedule.Task;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 定时任务注册类
 *
 * @author zw562
 */
@Component
public class CronTaskRegistrar implements DisposableBean {

    private final Map<Runnable, ScheduledTask> schedulerTaskMap = new ConcurrentHashMap<>(16);


    @Resource
    private TaskScheduler scheduler;

    public TaskScheduler getScheduler(){
        return this.scheduler;
    }

    public void addCronTask(Runnable runnable,String cornExpression){
        addCronTask(new CronTask(runnable, cornExpression));
    }

    public void addCronTask(CronTask cronTask){
        if(cronTask !=null){
            final Runnable runnable = cronTask.getRunnable();
            if(this.schedulerTaskMap.containsKey(runnable)){
                removeCronTask(runnable);
            }
            this.schedulerTaskMap.put(cronTask.getRunnable(),scheduledTask(cronTask));
        }
    }

    public void removeCronTask(Runnable task){
        final ScheduledTask remove = this.schedulerTaskMap.remove(task);
        if(remove!=null){
           remove.cancel();
        }
    }

    public ScheduledTask scheduledTask(CronTask cronTask) {
        ScheduledTask task = new ScheduledTask(cronTask);
        task.future = this.scheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());
        return task;
    }

    @Override
    public void destroy() {
        for (ScheduledTask task : this.schedulerTaskMap.values()) {
            task.cancel();
        }

        this.schedulerTaskMap.clear();
    }
}
