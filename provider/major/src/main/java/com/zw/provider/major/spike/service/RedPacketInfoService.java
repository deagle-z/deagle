package com.zw.provider.major.spike.service;

import com.zw.provider.major.spike.entity.RedPacketInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zw.provider.major.spike.vo.OpenRedPacketVO;

import java.math.BigDecimal;

/**
 * <p>
 * 红包信息
表，新建⼀个红包插⼊⼀条记录 服务类
 * </p>
 *
 * @author zw
 * @since 2020-09-02
 */
public interface RedPacketInfoService extends IService<RedPacketInfoEntity> {

	boolean sendRedPackage(BigDecimal totalAmount, Integer totalPacket, String userId);

	OpenRedPacketVO openRedPacket(Long redPacketId, String userId);
}
