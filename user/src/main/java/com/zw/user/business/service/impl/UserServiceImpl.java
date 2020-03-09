package com.zw.user.business.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zw.user.business.entity.User;
import com.zw.user.business.mapper.UserMapper;
import com.zw.user.business.service.UserService;
import org.springframework.stereotype.Service;

/**
  * user 测试
  * @date 2019/12/26
  * @author zw
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
