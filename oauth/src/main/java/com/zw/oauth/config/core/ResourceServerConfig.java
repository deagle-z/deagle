package com.zw.oauth.config.core;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 资源服务配置
 *
 * @author zw
 * @date 2020/8/27
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig  extends ResourceServerConfigurerAdapter {

//    @Autowired
//    private OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler;
    @Resource
    DruidDataSource dataSource;
    @Resource
    TokenStore tokenStore;

    /**
     * 记住我功能的token存取器配置
     *
     * @return the persistent token repository
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {

        final JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(this.dataSource);
//        tokenRepository.setCreateTableOnStartup(true); // 第一次启动创建
        return tokenRepository;
    }


    @Override
    public void configure(final ResourceServerSecurityConfigurer resources) {
        resources
//                .expressionHandler(this.oAuth2WebSecurityExpressionHandler)
                .resourceId("auth");
//        .tokenStore(tokenStore)
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http.addFilterAt(xxFilter,UsernamePasswordAuthenticationFilter)
        http.headers().frameOptions().disable()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/druid/**").permitAll()
                .antMatchers("/User/Register", "/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui", "/swagge‌​r-ui.html", "/sysdic/api_v1/getAllSysDicList", "/uniauth/api_v/test", "/uniauth/api_v/oauth/login", "/rest/api_v1/sso", "/rest/api_v1/getSessionData", "/rest/api_v1/acquireSessionData", "/rest/api_v1/pendingRedirect/**", "/uniauth/api_v/image/code", "/rest/pendingRedirect/**").permitAll()
                .anyRequest().authenticated();
    }
}
