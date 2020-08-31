package com.zw.schedule.Task;

import com.zw.schedule.config.SpringContextUtils;
import com.zw.util.StringUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 *
 *
 * @author zw562
 */
@AllArgsConstructor
@DependsOn("SpringContextUtils")
public class SchedulingRunnable implements Runnable {

    public static final Logger logger = LoggerFactory.getLogger(SchedulingRunnable.class);

    private String beanName;

    private String methodName;

    private String params;

    public SchedulingRunnable(String beanName,String methodName){
        this.beanName = beanName;
        this.methodName = methodName;
    }

    @Override
    public void run() {
        logger.info("定时任务开始执行 - bean：{},methodName：{},params：{}", beanName, methodName, params);
        final long startTime = System.currentTimeMillis();
        Method method=null;
        try {
            final Object target = SpringContextUtils.getBean(beanName);
            if(StringUtil.isNotEmpty(params)){
                method = target.getClass().getDeclaredMethod(methodName, String.class);
            }else{
                method = target.getClass().getDeclaredMethod(methodName,null);
            }
            ReflectionUtils.makeAccessible(method);
            if(StringUtil.isNotEmpty(params)){
                method.invoke(target, params);
            }else{
                method.invoke(target);
            }
        } catch (Exception e) {
            logger.error("定时任务执行异常：-bean: {},-method: {},-params: {},-e: {}",beanName,method,params,e);
        }
        long times = System.currentTimeMillis() - startTime;
        logger.info("定时任务执行结束 - bean：{}，方法：{}，参数：{}，耗时：{} 毫秒", beanName, methodName, params, times);
    }
}
