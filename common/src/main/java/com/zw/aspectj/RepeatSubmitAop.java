package com.zw.aspectj;

import com.zw.annotation.NoRepeatSubmit;
import com.zw.util.RedissonUtil;
import com.zw.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * aop-接口防重复提交
 *
 * @author zw
 * @date 2019/12/21
 */
@Aspect
@Component
@Slf4j
public class RepeatSubmitAop {

    @Resource
    private RedissonUtil redissonUtil;

    /**
     * 定义切点 被@NoRepeatSubmit标识的方法
     *
     * @param noRepeatSubmit 注解
     * @date 2019/12/21
     */
    @Pointcut("@annotation(noRepeatSubmit)")
    public void pointCut(NoRepeatSubmit noRepeatSubmit) {
    }

    /**
     * 环绕通知
     *
     * @param point          切点
     * @param noRepeatSubmit 注解
     * @return Object
     * @date 2019/12/21
     */
    @Around(value = "pointCut(noRepeatSubmit)", argNames = "point,noRepeatSubmit")
    public Object around(ProceedingJoinPoint point, NoRepeatSubmit noRepeatSubmit) throws Throwable {
        int lockTime = noRepeatSubmit.lockTime();
        final HttpServletRequest request = RequestUtils.getRequest();
        Assert.notNull(request, "request can not null");
        //todo 待优化 不止从header中获取token
        final String token = request.getHeader("Authorization");
        final String servletPath = request.getServletPath();
        //由token和url拼成key
        String key = token + servletPath;
        RLock rLock = redissonUtil.tryLock(key, (long) lockTime);
        try {
            if (rLock != null) {
                log.info("tryLock success,key is [{}],lockTime [{}]", key, lockTime);
                return point.proceed();
            }
        } finally {
            if (rLock != null) {
                rLock.unlock();
            }
        }
        return "请勿重复点击！";
    }
}
