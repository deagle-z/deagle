package com.zw.account.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zw.account.business.entity.Account;

public interface AccountService extends IService<Account> {
    /**
     * 从用户账户中借出
     */
    void debit(String userId, int money);
}
