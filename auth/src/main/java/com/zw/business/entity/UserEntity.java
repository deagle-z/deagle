package com.zw.business.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("a_user")
@ApiModel(value="UserEntity对象", description="")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private String userName;

    private String password;

    private String passwordSalt;

    @ApiModelProperty(value = "账户")
    private String account;

    private String phone;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    private Long createBy;

    private LocalDateTime createDate;

    private Long updateBy;

    private LocalDateTime updateDate;

    private String deleteFlag;


}
