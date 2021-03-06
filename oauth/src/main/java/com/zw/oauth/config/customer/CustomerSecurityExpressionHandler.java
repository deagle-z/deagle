package com.zw.oauth.config.customer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;



/**
 * 资源权限配置
 *
 * @author zw
 * @date 2020/8/28
 */
@Configuration
public class CustomerSecurityExpressionHandler extends OAuth2WebSecurityExpressionHandler{
    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(final ApplicationContext applicationContext) {
        final OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }
}
