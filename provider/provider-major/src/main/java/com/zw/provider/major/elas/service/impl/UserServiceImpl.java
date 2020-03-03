package com.zw.provider.major.elas.service.impl;

import com.zw.provider.major.elas.dao.UserReponsitory;
import com.zw.provider.major.elas.entity.User;
import com.zw.provider.major.elas.service.UserService;

import javax.annotation.Resource;

/**
  * es 测试
  * @date 2020/1/2
  * @author zw
*/

public class UserServiceImpl  implements UserService {

    @Resource
    private UserReponsitory userReponsitory;

    @Override
    public User add(User user){
         return userReponsitory.save(user);
    }
}
