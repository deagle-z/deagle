package com.zw.user.business.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zw.auth.business.service.UserService;
import org.springframework.stereotype.Service;

/**
  * user 测试
  * @date 2019/12/26
  * @author zw
*/
@Service
public class UserServiceImpl extends ServiceImpl<com.zw.auth.mapper.UserMapper, com.zw.auth.entity.User> implements UserService {
}
