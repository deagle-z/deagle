package com.zw.account.business.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.zw.account.business.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;


@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    private AccountService accountService;


    @PostMapping("/debit")
    public R demo(@RequestBody Map<String,Object> params){
        accountService.debit(params.get("userId").toString(),Integer.parseInt(params.get("money").toString()));
        return new R().setCode(200).setMsg("success");
    }
}
