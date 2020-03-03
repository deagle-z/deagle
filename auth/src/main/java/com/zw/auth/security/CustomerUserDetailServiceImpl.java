package com.zw.auth.config;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zw.auth.business.service.UserService;
import com.zw.exception.BusinessException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.zw.auth.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;

/**
  * 重写 userDetailService
  * @date 2020/3/3
  * @author zw
*/
@Component
@Slf4j
public class CustomerUserDetailServiceImpl implements UserDetailsService {
    @Resource
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("账号:{},开始登录",username);
        final User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getName, username));
        if (StringUtils.isEmpty(user.getPassword())) {
            throw new BusinessException("账号或密码错误");
        }
        UserDetail userDetail = new UserDetail();
        userDetail.setUsername(username);
        userDetail.setPassword(user.getPassword());
        return userDetail;
    }

    @Data
    private class UserDetail implements UserDetails{
        private String username;

        private String password;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public boolean isAccountNonExpired() {
            return false;
        }

        @Override
        public boolean isAccountNonLocked() {
            return false;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return false;
        }

        @Override
        public boolean isEnabled() {
            return false;
        }
    }
}
