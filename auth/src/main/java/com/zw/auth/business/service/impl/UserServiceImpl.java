package com.zw.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zw.auth.entity.User;
import com.zw.auth.mapper.UserMapper;
import com.zw.auth.service.UserService;
import org.springframework.stereotype.Service;

/**
  * user 测试
  * @date 2019/12/26
  * @author zw
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
