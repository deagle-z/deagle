package com.zw.oauth.config.core;

import com.zw.oauth.config.CustomerPasswordEncode;
import com.zw.oauth.config.CustomerUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * security 安全配置器
 *
 * @author zw
 * @date 2020/8/19
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomerUserDetailService userDetailsService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(new CustomerPasswordEncode());
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/", "/user/**");
//        super.configure(web);
//    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http
                // 请求授权
                .authorizeRequests()
                .antMatchers("/oauth/token").permitAll()
                // 任何请求
                .anyRequest()
                // 需要身份认证
                .authenticated()
                .and()
                // 关闭跨站请求防护
                .csrf()
                .disable();
    }
}
