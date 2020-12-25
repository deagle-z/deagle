package com.zw.provider.major.config.cache.support;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * CacheMessageListener
 *
 * @author zw
 * @date 2020/12/23
 */
@Slf4j
@RequiredArgsConstructor
public class CacheMessageListener implements MessageListener {

    private final RedisTemplate<Object, Object> redisTemplate;

    private final RedisCaffeineCacheManager redisCaffeineCacheManager;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        CacheMessage cacheMessage = (CacheMessage) redisTemplate.getValueSerializer().deserialize(message.getBody());
        log.debug("receive a redis topic message, clear local cache, the cacheName is {}, the key is {}",
                cacheMessage.getCacheName(), cacheMessage.getKey());
        redisCaffeineCacheManager.clearLocal(cacheMessage.getCacheName(), cacheMessage.getKey());
    }
}
