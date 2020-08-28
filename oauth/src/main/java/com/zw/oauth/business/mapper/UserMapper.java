package com.zw.oauth.business.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.oauth.business.pojo.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zw
 * @since 2020-08-06
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

}
