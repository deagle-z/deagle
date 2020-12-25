package com.zw.provider.major.config.cache.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * CacheMessage
 *
 * @author zw
 * @date 2020/12/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CacheMessage implements Serializable {
    private String cacheName;

    private Object key;
}
