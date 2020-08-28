package com.zw.oauth.business.service;


import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zw.oauth.business.pojo.entity.UserEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zw
 * @since 2020-08-06
 */
public interface UserService extends IService<UserEntity> {

    /**
     *
     * 获取参数map
     *
     * @return Map<SFunction<UserEntity,?>,Object>
     */
    static  Map<SFunction<UserEntity,?>,Object> paramMap(){
        return new HashMap<>(16);
    }


    /**
     * 获取单个用户
     *
     *
     * @param paramMap 参数map
     * @return UserEntity
     */
    UserEntity selectOne(Map<SFunction<UserEntity, ?>, Object> paramMap);


    /**
     * 注册用户
     *
     * @author zw
     * @date 2020/8/26
     */
    boolean registered(String userName, String password, String code) ;
}
