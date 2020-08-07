package com.zw.business.mapper;

import com.zw.business.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * user Mapper 接口
 * </p>
 *
 * @author zw
 * @since 2020-08-06
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

}
