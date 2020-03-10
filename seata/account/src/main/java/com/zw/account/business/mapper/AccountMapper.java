package com.zw.account.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.account.business.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountMapper  extends BaseMapper<Account> {

    @Update("update account_tbl set money = money - #{money} where user_id =#{userId} ")
    int updateMoney(String userId,Integer money);
}
