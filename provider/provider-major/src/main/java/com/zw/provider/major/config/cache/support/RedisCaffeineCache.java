package com.zw.provider.major.config.cache.support;

import com.github.benmanes.caffeine.cache.Cache;
import com.zw.provider.major.config.cache.properties.CacheConfigProperties;
import com.zw.util.LocalDateTimeUtil;
import io.micrometer.core.lang.NonNull;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * caffeine + redis 二级缓存 多级读取、过期策略
 *
 * @author zw
 * @date 2020/12/23
 */
@Slf4j
public class RedisCaffeineCache extends AbstractValueAdaptingCache {

    private final String name;

    @Getter
    private final Cache<Object, Object> caffeineCache;

    private final RedisTemplate<Object, Object> stringKeyRedisTemplate;

    private final String cachePrefix;

    private final long defaultExpiration;

    private final Map<String, Long> expires;

    private final String topic;

    private final Map<String, ReentrantLock> keyLockMap = new ConcurrentHashMap<>();

    public RedisCaffeineCache(String name, RedisTemplate<Object, Object> stringKeyRedisTemplate,
                              Cache<Object, Object> caffeineCache, CacheConfigProperties cacheConfigProperties) {
        super(cacheConfigProperties.isCacheNullValues());
        this.name = name;
        this.stringKeyRedisTemplate = stringKeyRedisTemplate;
        this.caffeineCache = caffeineCache;
        this.cachePrefix = cacheConfigProperties.getCachePrefix();
        this.defaultExpiration = cacheConfigProperties.getRedis().getDefaultExpiration();
        this.expires = cacheConfigProperties.getRedis().getExpires();
        this.topic = cacheConfigProperties.getRedis().getTopic();
        log.debug("时间:{}----------执行RedisCaffeineCache构造-------------", LocalDateTimeUtil.nowTime());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(@NonNull Object key,@NonNull Callable<T> valueLoader) {
        log.debug("时间:{}----------执行RedisCaffeineCache get 方法-------------", LocalDateTimeUtil.nowTime());
        Object value = lookup(key);
        if (value != null) {
            return (T) value;
        }

        log.trace("create lock for key : {}", key.toString());
        //如果 keyLockMap 为空 则将后面的值 存入keyLockMap 同时返回 后面的值
        ReentrantLock lock = keyLockMap.computeIfAbsent(key.toString(), s -> new ReentrantLock());
        try {
            lock.lock();
            value = lookup(key);
            if (value != null) {
                return (T) value;
            }
            value = valueLoader.call();
            //返回 NullValue.INSTANCE
            Object storeValue = toStoreValue(value);
            put(key, storeValue);
            return (T) value;
        }catch (Exception e) {
            throw new ValueRetrievalException(key, valueLoader, e.getCause());
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void put(@NonNull Object key, Object value) {
        log.debug("时间:{}----------执行RedisCaffeineCache put 方法-------------", LocalDateTimeUtil.nowTime());
        //如果 value 为null 并且 isAllowNullValues 是 false 则 清除本地缓存 往redis中塞一个 CacheMessage
        if (!super.isAllowNullValues() && value == null) {
            this.evict(key);
            return;
        }
        long expire = getExpire();
        if (expire > 0) {
            stringKeyRedisTemplate.opsForValue().set(getKey(key), toStoreValue(value), expire, TimeUnit.MILLISECONDS);
        }
        else {
            stringKeyRedisTemplate.opsForValue().set(getKey(key), toStoreValue(value));
        }

        push(new CacheMessage(this.name, key));

        caffeineCache.put(key, value);
    }

    @Override
    public ValueWrapper putIfAbsent(@NonNull Object key, Object value) {
        log.debug("时间:{}----------执行RedisCaffeineCache putIfAbsent 方法-------------", LocalDateTimeUtil.nowTime());
        Object cacheKey = getKey(key);
        Object prevValue;
        // 考虑使用分布式锁，或者将redis的setIfAbsent改为原子性操作
        synchronized (this) {
            prevValue = stringKeyRedisTemplate.opsForValue().get(cacheKey);
            if (prevValue == null) {
                long expire = getExpire();
                if (expire > 0) {
                    stringKeyRedisTemplate.opsForValue().set(getKey(key), toStoreValue(value), expire,
                            TimeUnit.MILLISECONDS);
                }
                else {
                    stringKeyRedisTemplate.opsForValue().set(getKey(key), toStoreValue(value));
                }

                push(new CacheMessage(this.name, key));

                caffeineCache.put(key, toStoreValue(value));
            }
        }
        return toValueWrapper(prevValue);
    }

    @Override
    public void evict(@NonNull Object key) {
        log.debug("时间:{}----------执行RedisCaffeineCache evict 方法-------------", LocalDateTimeUtil.nowTime());
        // 先清除redis中缓存数据，然后清除caffeine中的缓存，避免短时间内如果先清除caffeine缓存后其他请求会再从redis里加载到caffeine中
        stringKeyRedisTemplate.delete(getKey(key));

        push(new CacheMessage(this.name, key));

        caffeineCache.invalidate(key);
    }

    @Override
    public void clear() {
        // 先清除redis中缓存数据，然后清除caffeine中的缓存，避免短时间内如果先清除caffeine缓存后其他请求会再从redis里加载到caffeine中
        Set<Object> keys = stringKeyRedisTemplate.keys(this.name.concat(":*"));

        if (!CollectionUtils.isEmpty(keys)){
            stringKeyRedisTemplate.delete(keys);
        }

        push(new CacheMessage(this.name, null));

        caffeineCache.invalidateAll();
    }

    @Override
    protected Object lookup(@NonNull Object key) {
        Object cacheKey = getKey(key);
        Object value = caffeineCache.getIfPresent(key);
        if (value != null) {
            log.debug("get cache from caffeine, the key is : {}", cacheKey);
            return value;
        }else{
            log.debug("do not get cache from caffeine, the key is : {}", cacheKey);

        }

        // 避免自动一个 RedisTemplate 覆盖失效
        stringKeyRedisTemplate.setKeySerializer(new StringRedisSerializer());
        value = stringKeyRedisTemplate.opsForValue().get(cacheKey);

        if (value != null) {
            log.debug("get cache from redis and put in caffeine, the key is : {}", cacheKey);
            caffeineCache.put(key, value);
        }else {
            log.debug("do not get cache from redis, the key is : {}", cacheKey);
        }
        return value;
    }

    /**
      * 拼接key, 'name' + : + key/(cachePrefix:key)
      *
      * @param key 自定义的key
      * @date 2020/12/23
      * @return 拼接的key,
      */
    private Object getKey(Object key) {
        return this.name.concat(":").concat(
                StringUtils.isEmpty(cachePrefix) ? key.toString() : cachePrefix.concat(":").concat(key.toString()));
    }

    private long getExpire() {
        Long cacheNameExpire = expires.get(this.name);
        return cacheNameExpire == null ? defaultExpiration : cacheNameExpire;
    }

    /**
     * 缓存变更时 通过 redis listen 通知其他节点清理本地缓存
     *
     * @param message
     * @date 2018年1月31日 下午3:20:28
     */
    private void push(CacheMessage message) {
        stringKeyRedisTemplate.convertAndSend(topic, message);
    }

    /**
     * @param key
     * @description 清理本地缓存
     * @author fuwei.deng
     * @date 2018年1月31日 下午3:15:39
     * @version 1.0.0
     */
    public void clearLocal(Object key) {
        log.debug("clear local cache, the key is : {}", key);
        if (key == null) {
            caffeineCache.invalidateAll();
        }
        else {
            caffeineCache.invalidate(key);
        }
    }
}
