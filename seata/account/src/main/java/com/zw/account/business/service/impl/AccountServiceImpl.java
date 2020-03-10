package com.zw.account.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zw.account.business.entity.Account;
import com.zw.account.business.mapper.AccountMapper;
import com.zw.account.business.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Resource
    private AccountMapper accountMapper;

    /**
     * 从用户账户中借出
     *
     * @param userId
     * @param money
     */
    @Override
    public void debit(String userId, int money) {
        accountMapper.updateMoney(userId, money);
    }
}
