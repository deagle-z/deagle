package com.zw.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zw.business.entity.UserEntity;
import com.zw.business.mapper.UserMapper;
import com.zw.business.service.UserService;
import com.zw.constant.CommonConstants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zw
 * @since 2020-08-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Resource
    UserMapper userMapper;


    /**
     *  获取带 DeleteFlag = 0 的LambdaQueryWrapper
     *
     * @return LambdaQueryWrapper<UserEntity>
     */
    static LambdaQueryWrapper<UserEntity> wrappers() {
        return Wrappers.<UserEntity>lambdaQuery().eq(UserEntity::getDeleteFlag, CommonConstants.NOT_DELETED);
    }


    /**
     *  根据字段获取参数
     *
     * @param params k字段  v值
     * @return UserEntity
     */
    @Override
    public UserEntity getOne(Map<SFunction<UserEntity, Object>, String> params) {
        final LambdaQueryWrapper<UserEntity> wrappers = wrappers();
        params.forEach(wrappers::eq);
        return userMapper.selectOne(wrappers);
    }

    /**
     *  根据字段获取参数
     *
     * @param params k字段  v值
     * @return UserEntity
     */
    @Override
    public List<UserEntity>  selectList(Map<SFunction<UserEntity, Object>, String> params){
        final LambdaQueryWrapper<UserEntity> wrappers = wrappers();
        params.forEach(wrappers::eq);
        return userMapper.selectList(wrappers);
    }
}
