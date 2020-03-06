package com.zw.auth.security.authentication;

import com.zw.auth.security.config.CustomPasswordEncoder;
import com.zw.auth.security.handler.ClientLogoutSuccessHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;

/**
 * Created by Tian Haobing on 2019/6/23
 */
@Configuration
public class AuthenticationBeanConfig {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    protected AuthenticationSuccessHandler authenticationSuccessHandler;

    @Resource
    protected AuthenticationFailureHandler authenticationFailureHandler;

//    /**
//     * 默认密码处理器
//     *
//     * @return 密码加密器
//     */
    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }

    /**
     * 退出时的处理策略配置
     *
     * @return logout success handler
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new ClientLogoutSuccessHandler();
    }

//    @Bean
//    public RestAuthenticationFilter restAuthenticationFilter() {
//        final RestAuthenticationFilter filter = new RestAuthenticationFilter();
//        filter.setAuthenticationSuccessHandler(this.authenticationSuccessHandler);
//        filter.setAuthenticationFailureHandler(this.authenticationFailureHandler);
//        filter.setAuthenticationManager(this.authenticationManager);
//        return filter;
//    }
}
