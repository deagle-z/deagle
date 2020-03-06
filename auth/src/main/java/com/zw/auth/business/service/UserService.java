package com.zw.auth.business.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zw.auth.business.entity.User;

/**
 * user 测试
 * @date 2019/12/26
 * @author zw
 */

public interface UserService extends IService<User> {

    User getUserByName(String name);
}
