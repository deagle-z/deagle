package com.zw.provider.major.config.cache.metrics;

import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.boot.actuate.metrics.cache.CacheMeterBinderProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 将RedisCaffeineCacheMeterBinderProvider（实现自CacheMeterBinderProvider）注入容器
 *
 * @author zw
 * @date 2020/12/23
 */

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({MeterBinder.class, CacheMeterBinderProvider.class})
public class RedisCaffeineCacheMeterConfiguration {
    @Bean
    public RedisCaffeineCacheMeterBinderProvider redisCaffeineCacheMeterBinderProvider() {
        return new RedisCaffeineCacheMeterBinderProvider();
    }

}
