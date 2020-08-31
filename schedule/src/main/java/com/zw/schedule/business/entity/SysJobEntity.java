package com.zw.schedule.business.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zw
 * @since 2020-08-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_job")
@ApiModel(value="sysJobEntity对象", description="")
public class SysJobEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId
    private Integer jobId;

    private String beanName;

    private String methodName;

    private String methodParams;

    private String cronExpression;

    private String remark;

    @ApiModelProperty(value = "0 正常 1 暂停")
    private Integer jobStatus;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
