package com.zw.util;

import com.zw.exception.BusinessException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * redisson 工具测试
 * redisson 分布式锁尽量使用待过期时间的分布式锁，
 * 因为一个线程上了锁只能由这个线程自己解，如果这个线程还没解就报错那么这个锁除非自己过期，不会释放
 *
 * @author zw
 * @date 2019/12/20
 */

@Component
public class RedissonUtil {

    @Resource
    RedissonClient redissonClient;

    /**
     * 尝试获得锁
     * 获得锁后注意释放
     *
     * @return boolean
     * @date 2019/12/21
     */
    public RLock tryLock(String key) {
        RLock lock = redissonClient.getLock(key);
        try {
            boolean b = lock.tryLock();
            if (b) {
                return lock;
            }else{
                throw new BusinessException("{}分布式锁被占用无法获取",key);
            }
        } catch (Exception e) {
            throw new BusinessException("获取分布式锁失败{}",e.toString());
        }
    }


    /**
     * 尝试获取锁 获得锁后锁住seconds秒
     *
     * @param key     k
     * @param seconds 秒
     * @return RLock
     * @date 2019/12/21
     */
    public RLock tryLock(String key, Long seconds) {
        RLock lock = redissonClient.getLock(key);
        try {
            boolean isLock = lock.tryLock(seconds, TimeUnit.SECONDS);
            if (isLock) {
                return lock;
            }
            throw new BusinessException("{}分布式锁被占用无法获取",key);
        } catch (Exception e) {
            throw new BusinessException("获取分布式锁失败{}", e.toString());
        }
    }




}
