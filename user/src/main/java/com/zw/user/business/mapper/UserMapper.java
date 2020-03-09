package com.zw.user.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.user.business.entity.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * user dao
 * @date 2019/12/26
 * @author zw
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
