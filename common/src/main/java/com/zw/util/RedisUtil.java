package com.zw.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author zw
 * @description redis工具类
 * @date 2019/12/17
 */

@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public boolean hasKey(String key) {
        Assert.notNull(key, "redis key 不可为null");
        return redisTemplate.hasKey(key);
    }

    public void set(String key, Object value) {
        Assert.notNull(key, "redis key 不可为null");
        Assert.notNull(value, "redis value 不可为null");
        redisTemplate.opsForValue().set(key, JSONObject.toJSONString(value));
    }

    public void expire(String key, Object value,  Integer seconds) {
        Assert.notNull(key, "redis key 不可为null");
        Assert.notNull(value, "redis value 不可为null");
        redisTemplate.opsForValue().set(key, JSONObject.toJSONString(value), seconds == null ? seconds : 5000);
    }

    public boolean del(String key) {
        Assert.notNull(key, "redis key 不可为null");
        if (hasKey(key)) {
            return redisTemplate.delete(key);
        }
        return false;
    }

    public String get( String key) {
        if (hasKey(key)) {
            return redisTemplate.opsForValue().get(key);
        }
        return "";
    }

    /**
     * 尝试获取分布式锁
     *
     * @param lockKey     锁
     * @param requestId   请求标识(默认当前服务的ip端口)
     * @param expireTime  超期时间
     * @param tryLockTime 尝试获取锁时间
     * @return 是否获取成功
     */
    @Deprecated
    public boolean tryGetDistributedLock( String lockKey,  String requestId,  Long expireTime,  Long tryLockTime) {
        if (StringUtils.isEmpty(lockKey) || StringUtils.isEmpty(requestId) || StringUtils.isEmpty(expireTime) || StringUtils.isEmpty(tryLockTime)) {
            return false;
        }
        final long start = System.currentTimeMillis();
        try {
            while (true) {
                Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(lockKey, requestId, expireTime, TimeUnit.SECONDS);
                if (Boolean.TRUE.equals(aBoolean)) {
                    return true;
                }
                final long end = System.currentTimeMillis();
                if (end - start > tryLockTime) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 释放分布式锁
     *
     * @param lockKey   锁
     * @param requestId 请求标识(默认当前服务的applicationName + ip + 端口)
     * @return 是否释放成功
     */
    @Deprecated
    public boolean releaseDistributedLock(String lockKey, String requestId) {
        Assert.notNull(lockKey,"lockKey 不可为空");
        Assert.notNull(requestId,"requestId 不可为空");
        if (get(lockKey).equals(requestId)) {
            return redisTemplate.delete(lockKey);
        }
        return false;
    }
}
