package com.zw.provider.major.spike.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 抢红包记
录表，抢⼀个红包插⼊⼀条记录
 * </p>
 *
 * @author zw
 * @since 2020-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("red_packet_record")
@ApiModel(value="RedPacketRecordEntity对象", description="抢红包记录表，抢⼀个红包插⼊⼀条记录")
public class RedPacketRecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "抢到红包的⾦额")
    private BigDecimal amount;

    @ApiModelProperty(value = "抢到红包的⽤户的⽤户 名")
    private String nickName;

    @ApiModelProperty(value = "抢到红包的⽤户的头像")
    private String imgUrl;

    @ApiModelProperty(value = "抢到红包⽤户的⽤户标识")
    private Long userId;

    @ApiModelProperty(value = "红包id，采⽤ timestamp+5位随机数")
    private Long redPacketId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateDate;


}
