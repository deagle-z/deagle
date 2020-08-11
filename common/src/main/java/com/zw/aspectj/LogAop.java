package com.zw.aspectj;

import com.alibaba.fastjson.JSON;
import com.zw.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义日子aop 计算方法执行时间  记录方法参数
 *
 * @author zw
 * @date 2019/12/21
 */
@Component
@Aspect
@Slf4j
public class LogAop {

    @Pointcut("@annotation(log)")
    public void logPointCut(Log log) {
    }

    @Around(value = "logPointCut(customerLog)", argNames = "point,customerLog")
    public void log(ProceedingJoinPoint point, Log customerLog) {
        long start = System.currentTimeMillis();
        //方法名
        String methodName = point.getSignature().getName();
        //接收参数
        Object[] args = point.getArgs();
        log.info("方法{}开始执行+++++++++++++++++++++++++++", methodName);
        if (args != null && args.length > 0) {
            log.info("参数{}++++++++++++++++++++++++++++", JSON.toJSONString(args));
        }
        try {
            Object proceed = point.proceed();
            log.info("方法{}执行完毕", methodName);
            if (proceed != null) {
                log.info("方法{}执行完毕，返回结果{}++++++++++++", methodName, JSON.toJSONString(proceed));
            }
        } catch (Throwable throwable) {
            log.info("方法{}执行出错+++++++++++++", methodName);
        }
        long end = System.currentTimeMillis();
        log.info("方法{}执行时间{}毫秒++++++++++++", methodName, end - start);
        System.out.println(end - start);
    }

    public static void main(String[] args) {
//        String da="2019-12-23 11:26:59";
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime nextTime = LocalDateTime.parse(da, dateTimeFormatter);
//        LocalDateTime now = LocalDateTime.now();
////        前面>后面 为负
//        Duration duration = Duration.between(now, nextTime);
//        System.out.println(duration.toDays());
//        System.out.println(duration.getSeconds());
//        Map<Object, Object> synchronizedMap = Collections.synchronizedMap(new HashMap<>(5));
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        List list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }
        list.parallelStream().forEach(data-> System.out.println(data));
        System.out.println("便利完成");
    }
}
