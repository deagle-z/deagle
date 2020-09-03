package com.zw.provider.major.spike.query;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 发送红包接受参数
 *
 * @author zw562
 */
@Data
public class SaveRedPackageQuery {

	@NotNull
	@Min(1)
	@Max(1000)
	private BigDecimal totalAmount;

	@NotNull
	@Min(1)
	@Max(100)
	private Integer totalPacket;

}
