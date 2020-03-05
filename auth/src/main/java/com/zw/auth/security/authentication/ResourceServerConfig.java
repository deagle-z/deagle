/*
 * COPYRIGHT China Mobile (SuZhou) Software Technology Co.,Ltd. 2018
 *
 * The copyright to the computer program(s) herein is the property of
 * CMSS Co.,Ltd. The programs may be used and/or copied only with written
 * permission from CMSS Co.,Ltd. or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which the program(s) have been
 * supplied.
 */

package com.chinamobile.cmss.cpms.common.auth.config;


import com.chinamobile.cmss.cpms.common.auth.filter.RestAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Create by Tianhaobing ON 2019/2/13
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler;

    @Autowired
    private AccessDeniedHandler cpmsAccessDeniedHandler;

    @Autowired
    protected AuthenticationSuccessHandler cpmsAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler cpmsAuthenticationFailureHandler;

    @Autowired
    private FormAuthenticationConfig formAuthenticationConfig;

    @Resource
    private RestAuthenticationFilter restAuthenticationFilter;
    @Resource
    private DataSource dataSource;

    /**
     * 记住我功能的token存取器配置
     *
     * @return the persistent token repository
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {

        final JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(this.dataSource);
        //		tokenRepository.setCreateTableOnStartup(true); // 第一次启动创建
        return tokenRepository;
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        this.formAuthenticationConfig.configure(http);
        http.addFilterAt(this.restAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().disable()
                .and()
                .exceptionHandling().accessDeniedHandler(this.cpmsAccessDeniedHandler)
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/druid/**").permitAll()
                .antMatchers("/user/register", "/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui", "/swagge‌​r-ui.html", "/sysdic/api_v1/getAllSysDicList", "/uniauth/api_v/test", "/uniauth/api_v/oauth/login", "/rest/api_v1/sso", "/rest/api_v1/getSessionData", "/rest/api_v1/acquireSessionData", "/rest/api_v1/pendingRedirect/**", "/uniauth/api_v/image/code", "/rest/pendingRedirect/**").permitAll()
                .antMatchers("/uniauth/api_v/org/getNextOrgList/**", "/uniauth/api_v/user/getByUserId/**", "/uniauth/api_v/user/getByUserIds/**", "/uniauth/api_v/org/getUserOrgList/**", "/uniauth/api_v/getUserOrgRoleInfo", "/uniauth/api_v/role/getUserOrgInfoList/**", "/uniauth/api_v/user/getByUserIds/**", "/uniauth/api_v/user/pageAllUserOrgByUserName/**", "/uniauth/api_v/org/pageAllOrgByOrgName/**", "/uniauth/api_v/role/getRoleByUserId/**", "/uniauth/api_v/org/getOrgMainPrincipal", "/uniauth/api_v/org/getOrgList/**", "/uniauth/api_v/role/getOrgUserList/**", "/uniauth/api_v/role/getOrgUserTree/**").authenticated()
                .anyRequest().authenticated();
        //authorizeConfigManager.config(http.authorizeRequests());

    }

    @Override
    public void configure(final ResourceServerSecurityConfigurer resources) {
        //resources.tokenExtractor(new CpmsBearerTokenExtractor());
        resources.expressionHandler(this.oAuth2WebSecurityExpressionHandler);
    }

}
