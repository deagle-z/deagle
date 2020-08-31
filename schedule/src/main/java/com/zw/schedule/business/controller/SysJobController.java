package com.zw.schedule.business.controller;


import com.zw.response.R;
import com.zw.schedule.Task.CronTaskRegistrar;
import com.zw.schedule.Task.SchedulingRunnable;
import com.zw.schedule.business.entity.SysJobEntity;
import com.zw.schedule.business.service.SysJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zw
 * @since 2020-08-31
 */
@RestController
@Api(value = "sysJob" ,tags = "")
@RequestMapping("/Schedule")
public class SysJobController {

    @Resource
    SysJobService sysJobService;
    @Resource
    CronTaskRegistrar registrar;

    @PostMapping("/AddTask")
    @ApiOperation(value = "", httpMethod = "POST", response = R.class, notes = "")
    public R<?> addScheduleTask(@RequestBody SysJobEntity sysJobEntity) {
        sysJobService.save(sysJobEntity);
        if("0".equals(sysJobEntity.getJobStatus())){
            SchedulingRunnable runnable = new SchedulingRunnable(sysJobEntity.getBeanName(),sysJobEntity.getMethodName(),sysJobEntity.getMethodParams());
            registrar.addCronTask(runnable,sysJobEntity.getCronExpression());
        }
        return R.success("新增成功");
    }

    @PostMapping("/UpdateTask")
    @ApiOperation(value = "", httpMethod = "POST", response = R.class, notes = "")
    public R<?> UpdateTask(@RequestBody SysJobEntity sysJobEntity) {
        final SysJobEntity oldTask = sysJobService.getById(sysJobEntity.getJobId());
        if("0".equals(oldTask.getJobStatus())){
            SchedulingRunnable runnable = new SchedulingRunnable(oldTask.getBeanName(), oldTask.getMethodName(), oldTask.getMethodParams());
            registrar.removeCronTask(runnable);
        }
        sysJobService.updateById(sysJobEntity);
        if("0".equals(sysJobEntity.getJobStatus())){
            SchedulingRunnable runnable = new SchedulingRunnable(sysJobEntity.getBeanName(), sysJobEntity.getMethodName(), sysJobEntity.getMethodParams());
            registrar.addCronTask(runnable,sysJobEntity.getCronExpression());
        }
        return R.success("修改成功");
    }


    @PostMapping("/DeleteSchedule")
    @ApiOperation(value = "", httpMethod = "POST", response = R.class, notes = "")
    public R<?> deleteSchedule(@RequestBody SysJobEntity sysJobEntity) {
        sysJobService.removeById(sysJobEntity.getJobId());
        if("0".equals(sysJobEntity.getJobStatus())){
            SchedulingRunnable runnable = new SchedulingRunnable(sysJobEntity.getBeanName(), sysJobEntity.getMethodName(), sysJobEntity.getMethodParams());
            registrar.removeCronTask(runnable);
        }
        return R.success();
    }

}
