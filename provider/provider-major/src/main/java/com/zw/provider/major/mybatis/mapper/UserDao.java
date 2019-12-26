package com.zw.provider.major.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.provider.major.mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
  * 测试 dao
  * @date 2019/12/26
  * @author zw
*/
@Mapper
public interface UserDao extends BaseMapper<User> {
}
