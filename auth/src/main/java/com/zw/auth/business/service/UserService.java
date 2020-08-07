package com.zw.auth.business.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zw.auth.business.entity.UserEntity;

import java.util.HashMap;
import java.util.List;
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
     * 获取参数map
     *
     * @return <SFunction<UserEntity, Object>, String>
     */
    static  Map<SFunction<UserEntity, Object>, String> paramsMap(){
        return new HashMap<>(10);
    }


    /**
     *  根据字段获取类
     *
     * @param params k字段  v值
     * @return UserEntity
     */
    UserEntity getOne(Map<SFunction<UserEntity, Object>, String> params);

    /**
     *  批量查询
     *
     * @param params
     * @return List<UserEntity>
     */
    List<UserEntity> selectList(Map<SFunction<UserEntity, Object>, String> params);
}
