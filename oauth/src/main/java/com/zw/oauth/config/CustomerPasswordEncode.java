package com.zw.oauth.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class CustomerPasswordEncode implements PasswordEncoder {
        @Override
        public String encode(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override
        public boolean matches(CharSequence charSequence, String s) {
//            final String string = charSequence.toString();
            return true;
        }
}
