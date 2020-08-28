package com.zw.oauth.business.controller;


import com.zw.oauth.business.pojo.bo.UserRegisterBO;
import com.zw.oauth.business.service.UserService;
import com.zw.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  User前端控制器
 * </p>
 *
 * @author zw
 * @since 2020-08-06
 */
@RestController
@RequestMapping("/User")
@Api(value = "User" ,tags = {"用户相关接口"})
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/Register")
    @ApiOperation(value = "注册", httpMethod = "POST", response = R.class, notes = "注册")
    public R<String> registered(@Validated @RequestBody UserRegisterBO bo) {
        userService.registered(bo.getAccount(), bo.getPassword(), bo.getCode());
        return R.success("注册成功！");
    }


}
