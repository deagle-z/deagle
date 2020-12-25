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
 * 红包信息
表，新建⼀个红包插⼊⼀条记录
 * </p>
 *
 * @author zw
 * @since 2020-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("red_packet_info")
@ApiModel(value="RedPacketInfoEntity对象", description="红包信息表，新建⼀个红包插⼊⼀条记录")
public class RedPacketInfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long redPacketId;

    @ApiModelProperty(value = "红包总⾦额，单位分")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "红包总个数")
    private Integer totalPacket;

    @ApiModelProperty(value = "剩余红包⾦额，单位分")
    private BigDecimal remainingAmount;

    @ApiModelProperty(value = "剩余红包个数")
    private Integer remainingPacket;

    @ApiModelProperty(value = "新建红包⽤户的⽤户标识")
    private String userId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateDate;


}
