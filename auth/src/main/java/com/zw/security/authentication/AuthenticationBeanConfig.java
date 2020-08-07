package com.zw.security.authentication;

import org.springframework.context.annotation.Configuration;


@Configuration
public class AuthenticationBeanConfig {


    /**
     * 默认密码处理器
     *
     * @return 密码加密器
     */
//    @Bean
//    @ConditionalOnMissingBean(PasswordEncoder.class)
//    public PasswordEncoder passwordEncoder() {
//        return new CustomPasswordEncoder();
//    }

    /**
     * 退出时的处理策略配置
     *
     * @return logout success handler
     */
//    @Bean
//    public LogoutSuccessHandler logoutSuccessHandler() {
//        return new ClientLogoutSuccessHandler();
//    }

//    @Bean
//    public RestAuthenticationFilter restAuthenticationFilter() {
//        final RestAuthenticationFilter filter = new RestAuthenticationFilter();
//        filter.setAuthenticationSuccessHandler(this.authenticationSuccessHandler);
//        filter.setAuthenticationFailureHandler(this.authenticationFailureHandler);
//        filter.setAuthenticationManager(this.authenticationManager);
//        return filter;
//    }
}
