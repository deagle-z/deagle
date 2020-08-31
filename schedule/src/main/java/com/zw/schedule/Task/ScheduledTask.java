package com.zw.schedule.Task;

import org.springframework.lang.Nullable;
import org.springframework.scheduling.config.Task;

import java.util.concurrent.ScheduledFuture;

public class ScheduledTask {
    private final Task task;
    @Nullable
    volatile ScheduledFuture<?> future;

    ScheduledTask(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return this.task;
    }

    public void cancel() {
        ScheduledFuture<?> future = this.future;
        if (future != null) {
            future.cancel(true);
        }
    }

    @Override
    public String toString() {
        return this.task.toString();
    }
}
