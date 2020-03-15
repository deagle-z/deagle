package com.zw.auth.security.authentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CusotmerVerification implements AuthenticationManager {
    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        //todo 在此处校验密码
        return null;
    }
}
