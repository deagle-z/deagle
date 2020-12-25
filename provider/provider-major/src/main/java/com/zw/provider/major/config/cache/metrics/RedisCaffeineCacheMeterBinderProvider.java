package com.zw.provider.major.config.cache.metrics;

import com.zw.provider.major.config.cache.support.RedisCaffeineCache;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.binder.cache.CaffeineCacheMetrics;
import lombok.NoArgsConstructor;
import org.springframework.boot.actuate.metrics.cache.CacheMeterBinderProvider;

/**
 * 实现CacheMeterBinderProvider,替代原有的 cache provider
 *
 * @author zw
 * @date 2020/12/23
 */
@NoArgsConstructor
public class RedisCaffeineCacheMeterBinderProvider implements CacheMeterBinderProvider<RedisCaffeineCache> {
    @Override
    public MeterBinder getMeterBinder(RedisCaffeineCache cache, Iterable<Tag> tags) {
        return new CaffeineCacheMetrics(cache.getCaffeineCache(), cache.getName(), tags);
    }
}
