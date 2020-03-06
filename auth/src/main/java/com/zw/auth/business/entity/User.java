package com.zw.auth.business.entity;

import lombok.Data;

@Data
public class User {
    private String username;

    private String password;

    private String sex;

    private String age;

    private String phone;

    private String status;
}
