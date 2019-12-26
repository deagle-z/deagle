package com.zw.provider.major.mybatis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zw.provider.major.mybatis.mapper.UserDao;
import com.zw.provider.major.mybatis.entity.User;
import com.zw.provider.major.mybatis.service.UserService;
import org.springframework.stereotype.Service;

/**
  * user 测试
  * @date 2019/12/26
  * @author zw
*/
@Service
public class UserServiceImpl  extends ServiceImpl<UserDao, User> implements UserService {
}
