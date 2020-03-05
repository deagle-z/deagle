package com.zw.auth.entity;

import com.zw.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
  * 用户实体类
  * @date 2020/3/3
  * @author zw
*/

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    private String name;

    private String password;

    private String sex;

    private String age;

    private String phone;
}
