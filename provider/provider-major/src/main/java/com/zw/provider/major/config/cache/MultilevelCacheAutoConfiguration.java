package com.zw.provider.major.config.cache;

import com.zw.provider.major.config.cache.properties.CacheConfigProperties;
import com.zw.provider.major.config.cache.support.CacheMessageListener;
import com.zw.provider.major.config.cache.support.RedisCaffeineCacheManager;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Objects;

/**
 * 注入 stringKeyRedisTemplate,RedisCaffeineCacheManager,RedisMessageListenerContainer
 *
 * @author zw
 * @date 2020/12/23
 */

@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(RedisAutoConfiguration.class)   //加载完配置文件后再加载文件
@EnableConfigurationProperties(CacheConfigProperties.class)
public class MultilevelCacheAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "stringKeyRedisTemplate")
    public RedisTemplate<Object, Object> stringKeyRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    @ConditionalOnBean(value = RedisTemplate.class,name = "stringKeyRedisTemplate")
    public RedisCaffeineCacheManager cacheManager(
            CacheConfigProperties cacheConfigProperties,
            RedisTemplate<Object, Object> stringKeyRedisTemplate) {
        return new RedisCaffeineCacheManager(cacheConfigProperties, stringKeyRedisTemplate);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            CacheConfigProperties cacheConfigProperties,
            RedisTemplate<Object, Object> stringKeyRedisTemplate,
            RedisCaffeineCacheManager redisCaffeineCacheManager) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(Objects.requireNonNull(stringKeyRedisTemplate.getConnectionFactory()));
        CacheMessageListener cacheMessageListener = new CacheMessageListener(stringKeyRedisTemplate,
                redisCaffeineCacheManager);
        redisMessageListenerContainer.addMessageListener(cacheMessageListener,
                new ChannelTopic(cacheConfigProperties.getRedis().getTopic()));
        return redisMessageListenerContainer;
    }
}
