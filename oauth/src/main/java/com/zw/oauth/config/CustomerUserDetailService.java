package com.zw.oauth.config;

import com.zw.oauth.business.dto.UserDetailsDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomerUserDetailService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetailsDto user = new UserDetailsDto();
        user.setUsername("root");
        user.setPassword("123456");
        return user;
    }
}
