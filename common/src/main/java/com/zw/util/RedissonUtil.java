package com.zw.util;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * redisson 工具测试
 * redisson 分布式锁尽量使用待过期时间的分布式锁，
 * 因为一个线程上了锁只能由这个线程自己解，如果这个线程还没解就报错那么这个锁除非自己过期，不会释放
 * @author zw
 * @date 2019/12/20
 */

@Component
public class RedissonUtil {

    @Resource
    RedissonClient redissonClient;


    /**
     * lock()强制上锁 如果获取不到会一直询问进入死循环 线程阻塞
     * 注意释放锁
     *
     * @param key redis key
     * @date 2019/12/20
     */
    public RLock lock(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.lock();
        return lock;
    }


    /**
     * 强制上锁一定时间
     * 注意释放锁
     *
     * @param key       redis key
     * @param leaseTime 上锁时间
     * @date 2019/12/20
     */
    public RLock lock(String key, Long leaseTime) {
        RLock lock = redissonClient.getLock(key);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return lock;
    }


    /**
     * 尝试获得锁
     * 获得锁后注意释放
     *
     * @return boolean
     * @date 2019/12/21
     */
    public boolean tryLock(String key) {
        RLock lock = redissonClient.getLock(key);
        try {
            return lock.tryLock();
        } catch (Exception e) {
            return false;
        }
    }


    /**
      * 尝试获取锁 获得锁后锁住seconds秒
      * @date 2019/12/21
      * @param key k
      * @param seconds 秒
      * @return RLock
    */
    public RLock tryLock(String key, Long seconds) {
        RLock lock = redissonClient.getLock(key);
        try {
            boolean isLock = lock.tryLock(seconds, TimeUnit.SECONDS);
            if(isLock){
                return lock;
            }else{
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
      * 解锁
      * @date 2019/12/21
      * @param key k
    */
    public void unLock(String key){
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }

}
