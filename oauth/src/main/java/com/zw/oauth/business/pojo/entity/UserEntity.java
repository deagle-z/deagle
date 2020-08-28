package com.zw.oauth.business.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
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
 * @since 2020-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oauth_user")
@ApiModel(value="UserEntity对象", description="")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long userId;

    private String userName;

    private String password;

    private String passwordSalt;

    @ApiModelProperty(value = "账户")
    private String account;

    private String phone;

    private String enabled;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;


}
