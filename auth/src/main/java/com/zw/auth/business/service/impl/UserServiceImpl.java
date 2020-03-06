package com.zw.auth.business.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zw.auth.business.entity.User;
import com.zw.auth.business.mapper.UserMapper;
import com.zw.auth.business.service.UserService;
import com.zw.base.entity.SecurityDetail;
import org.springframework.stereotype.Service;

/**
  * user 测试
  * @date 2019/12/26
  * @author zw
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public User getUserByName(String name) {
        return this.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, name));
    }
}
