package com.zw.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author zw
 * @description redis工具类
 * @date 2019/12/17
 */

public class RedisUtils {

    @Resource
    private RedisTemplate redisTemplate;


    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, JSONObject.toJSONString(value));
    }

    public void expire(String key, Object value,Integer seconds){
        redisTemplate.opsForValue().set(key, JSONObject.toJSONString(value),seconds);
    }

    public boolean del(String key) {
        if(hasKey(key)){
            return redisTemplate.delete(key);
        }
        return false;
    }

    public String get(String key){
        if (hasKey(key)) {
            return redisTemplate.opsForValue().get(key).toString();
        }
        return "";
    }


}
