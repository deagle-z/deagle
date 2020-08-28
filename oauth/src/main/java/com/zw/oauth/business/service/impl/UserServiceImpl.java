package com.zw.oauth.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zw.oauth.business.mapper.UserMapper;
import com.zw.oauth.business.pojo.entity.UserEntity;
import com.zw.oauth.business.service.UserService;
import com.zw.oauth.util.EncryptAndDecryptUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zw
 * @since 2020-08-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {


    /**
     * 获取单个用户
     *
     * @param paramMap 参数map
     * @return UserEntity
     */
    @Override
    public UserEntity selectOne(Map<SFunction<UserEntity,?>,Object> paramMap) {
        final LambdaQueryWrapper<UserEntity> wrapper = Wrappers.lambdaQuery();
        paramMap.forEach(wrapper::eq);
        return getOne(wrapper);
    }

    /**
     * 注册用户
     *
     * @param userName 用户名/账号
     * @param password 密码
     * @date 2020/8/26
     */
    @Override
    public boolean registered(String userName, String password, String code)  {
        UserEntity user = new UserEntity();
        user.setUserId(IdWorker.getId());
        user.setAccount(userName);
        user.setUserName(userName);

        //设置密码
        final String salt = UUID.randomUUID().toString();
        user.setPassword(EncryptAndDecryptUtils.md5Encrypt(password + "_" + salt));
        user.setPasswordSalt(salt);

        return this.save(user);
    }
}
