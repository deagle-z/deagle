package com.zw.provider.major.config.cache.properties;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * redis 相关配置
 *
 * @author zw
 * @date 2020/12/23
 */
@Data
public class RedisConfigProp {

    /**
     * 全局过期时间，单位毫秒，默认不过期
     */
    private  long defaultExpiration = 0;

    /**
     * 每个cacheName的过期时间，单位毫秒，优先级比defaultExpiration高
     */
    private  Map<String, Long> expires = new HashMap<>();

    /**
     * 缓存更新时通知其他节点的topic名称
     */
    private  String topic = "cache:redis:caffeine:topic";
}
