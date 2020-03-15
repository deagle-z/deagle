package com.zw.auth.security.authentication;

import com.zw.auth.security.config.CustomPasswordEncoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
  * 配置security
  * @date 2020/3/5
  * @author zw
*/
public class AuthSecurityConfigurer extends WebSecurityConfigurerAdapter {
    //    @Resource
//    private AuthenticationManager authenticationManager;
//    @Resource
//    protected AuthenticationSuccessHandler authenticationSuccessHandler;
//
//    @Resource
//    protected AuthenticationFailureHandler authenticationFailureHandler;
//    @Bean
//    @ConditionalOnMissingBean(PasswordEncoder.class)
//    public PasswordEncoder passwordEncoder() {
//
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public BCryptPasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }

    /**
     * 注入 AuthenticationManager 用于密码模式
     *
     * @author zw
     * @date 2020/3/7
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /*@Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    */

//    @Bean
//    public RestAuthenticationFilter restAuthenticationFilter() {
//        final RestAuthenticationFilter filter = new RestAuthenticationFilter();
//        filter.setAuthenticationSuccessHandler(this.authenticationSuccessHandler);
//        filter.setAuthenticationFailureHandler(this.authenticationFailureHandler);
//        filter.setAuthenticationManager(this.authenticationManager);
//        return filter;
//    }


    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http
                // 请求授权
                .authorizeRequests()
                // 任何请求
                .anyRequest()
                // 需要身份认证
                .authenticated().and()
                // 关闭跨站请求防护
                .csrf().disable();
    }
}
