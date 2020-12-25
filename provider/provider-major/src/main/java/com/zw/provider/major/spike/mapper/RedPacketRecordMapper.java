package com.zw.provider.major.spike.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.provider.major.spike.entity.RedPacketRecordEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 抢红包记
录表，抢⼀个红包插⼊⼀条记录 Mapper 接口
 * </p>
 *
 * @author zw
 * @since 2020-09-02
 */
@Mapper
public interface RedPacketRecordMapper extends BaseMapper<RedPacketRecordEntity> {

}
