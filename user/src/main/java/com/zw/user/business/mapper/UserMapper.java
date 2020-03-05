package com.zw.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.auth.entity.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * user dao
 * @date 2019/12/26
 * @author zw
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
