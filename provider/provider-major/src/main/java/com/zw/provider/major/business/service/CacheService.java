package com.zw.provider.major.business.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 缓存测试
 *
 * @author zw
 * @date 2020/12/24
 */
@Service
public class CacheService {


    @Cacheable(value = {"cacheTest"},key ="#id" )
    public String cacheSuccess(String id){
        return "fail";
    }
}
