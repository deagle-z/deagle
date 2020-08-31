package com.zw.schedule.Task;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zw.schedule.business.entity.SysJobEntity;
import com.zw.schedule.business.service.SysJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zw562
 */
@Component
public class SysJobRunner implements CommandLineRunner {
    public static final Logger logger = LoggerFactory.getLogger(SysJobRunner.class);


    @Resource
    SysJobService sysJobService;
    @Resource
    CronTaskRegistrar cronTaskRegistrar;

    @Override
    public void run(String... args) throws Exception {
        final List<SysJobEntity> list = sysJobService.list(Wrappers.<SysJobEntity>lambdaQuery().eq(SysJobEntity::getJobStatus, "0"));
        if(CollectionUtils.isNotEmpty(list)){
            for (SysJobEntity job : list) {
                SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
                cronTaskRegistrar.addCronTask(task, job.getCronExpression());
            }
        }
    }
}
