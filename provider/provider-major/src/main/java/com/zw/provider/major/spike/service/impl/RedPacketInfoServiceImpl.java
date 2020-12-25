package com.zw.provider.major.spike.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zw.exception.BusinessException;
import com.zw.provider.major.spike.entity.RedPacketInfoEntity;
import com.zw.provider.major.spike.entity.RedPacketRecordEntity;
import com.zw.provider.major.spike.mapper.RedPacketInfoMapper;
import com.zw.provider.major.spike.service.RedPacketInfoService;
import com.zw.provider.major.spike.service.RedPacketRecordService;
import com.zw.provider.major.spike.vo.OpenRedPacketVO;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 红包信息表，新建⼀个红包插⼊⼀条记录 服务实现类
 * </p>
 *
 * @author zw
 * @since 2020-09-02
 */
@Service
public class RedPacketInfoServiceImpl extends ServiceImpl<RedPacketInfoMapper, RedPacketInfoEntity> implements RedPacketInfoService {
	public static final String MIN_AMOUNT = "0.01";

	@Resource
	RedissonClient redissonClient;
	@Resource
	RedPacketRecordService redPacketRecordService;

	/**
	 * 发红包
	 *
	 * @param totalAmount 红包总金额
	 * @param totalPacket 红包总计数量
	 */
	@Override
	public boolean sendRedPackage(BigDecimal totalAmount, Integer totalPacket, String userId) {
		RedPacketInfoEntity redPacketInfo = new RedPacketInfoEntity();
		final long id = IdWorker.getId();
		redPacketInfo.setRedPacketId(id);
		redPacketInfo.setTotalAmount(totalAmount);
		redPacketInfo.setTotalPacket(totalPacket);
		redPacketInfo.setRemainingAmount(totalAmount);
		redPacketInfo.setRemainingPacket(totalPacket);
		redPacketInfo.setUserId(userId);
		final boolean saveResult = this.save(redPacketInfo);
		//往redis中存红包个数和红包金额
		redissonClient.getBucket("red_packet_amount_" + id).set(totalAmount);
		redissonClient.getBucket("red_packet_packet_" + id).set(totalPacket);
		return saveResult;
	}


	/**
	 * 抢红包
	 *
	 * @param redPacketId 红包id
	 * @param userId      抢红包user
	 * @return OpenRedPacketVO
	 */
	@Override
	public OpenRedPacketVO openRedPacket(Long redPacketId, String userId) {
		OpenRedPacketVO redPacketVO = new OpenRedPacketVO();
		RedPacketRecordEntity record = new RedPacketRecordEntity();

		//该用户是否已抢过该红包
		// final RedPacketRecordEntity one = redPacketRecordService.getOne(Wrappers.<RedPacketRecordEntity>lambdaQuery().eq(RedPacketRecordEntity::getUserId, userId).eq(RedPacketRecordEntity::getRedPacketId, redPacketId));
		// if(null==one){
		// 	throw new BusinessException("你已经抢过该红包了！");
		// }

		final RLock lock = redissonClient.getLock("red_packet_" + redPacketId);
		try {
			final boolean isLock = lock.tryLock(10, 5, TimeUnit.SECONDS);
			if (!isLock) {
				throw new BusinessException("服务器异常,请稍后重试！");
			}
			final RBucket<Integer> totalPacketBucket = redissonClient.getBucket("red_packet_packet_" + redPacketId);
			final RBucket<BigDecimal> totalAmountBucket = redissonClient.getBucket("red_packet_amount_" + redPacketId);
			//剩余红包总金额
			final BigDecimal totalAmount = totalAmountBucket.get();
			//剩余红包总数
			final Integer totalPacket = totalPacketBucket.get();
			if (null==totalAmount || totalAmount.compareTo(BigDecimal.ZERO) <= 0 || null == totalPacket || totalPacket <= 0) {
				throw new BusinessException("红包已经被抢完了！");
			}

			//计算本次抢红包金额
			if (totalPacket.equals(1)) {
				//红包只剩一个
				redPacketVO.setAmount(totalAmount);
				//更新db
				RedPacketInfoEntity redPacketInfoEntity = new RedPacketInfoEntity();
				redPacketInfoEntity.setRedPacketId(redPacketId);
				redPacketInfoEntity.setRemainingPacket(0);
				redPacketInfoEntity.setRemainingAmount(BigDecimal.ZERO);
				this.updateById(redPacketInfoEntity);
				record.setAmount(totalAmount.setScale(2, RoundingMode.HALF_UP));

				//清楚redis数据
				totalPacketBucket.delete();
				totalAmountBucket.delete();
				System.out.println("--------------------mount:" + totalAmount);
			} else {
				//额度在0.01和(剩余平均值*2)
				final ThreadLocalRandom random = ThreadLocalRandom.current();
				BigDecimal max = totalAmount.divide(new BigDecimal(totalPacket),4).multiply(new BigDecimal("2")).setScale(2, RoundingMode.HALF_UP);
				final double randomAmount = random.nextDouble(0.01D, max.doubleValue());
				BigDecimal amount = randomAmount <= 0.01D ? new BigDecimal(MIN_AMOUNT) : new BigDecimal(String.valueOf(randomAmount)).setScale(2, RoundingMode.HALF_UP);
				if (amount.compareTo(totalAmount) >= 0) {
					amount = totalAmount.subtract(new BigDecimal(MIN_AMOUNT));
				}
				redPacketVO.setAmount(amount);
				record.setAmount(amount);

				//红包数量减一
				totalPacketBucket.set(totalPacket - 1);
				//金额重新计算存入
				totalAmountBucket.set(totalAmount.subtract(amount));
				System.out.println("--------------------mount:" + amount);
			}
			//新增抢红包记录
			record.setRedPacketId(redPacketId);

		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			log.error("--------------e:{}",e.getCause());
			throw new BusinessException("服务器异常,请稍后重试！");
		} finally {
			lock.unlock();
		}
		redPacketRecordService.save(record);
		return redPacketVO;
	}
}
