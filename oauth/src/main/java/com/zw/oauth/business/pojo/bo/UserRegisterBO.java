package com.zw.oauth.business.pojo.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * 用户注册参数接收
 *
 * @author zw
 * @date 2020/8/26
 */
@Data
public class UserRegisterBO {

    @NotNull(message = "用户名不可为空")
    @NotEmpty(message = "用户名不可为空")
    private String account;

    @NotNull(message = "密码不可为空")
    @NotEmpty(message = "密码不可为空")
    private String password;

    @ApiModelProperty("验证码")
    private String code;
}
