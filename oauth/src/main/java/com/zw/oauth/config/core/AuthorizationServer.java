package com.zw.oauth.config.core;


import com.alibaba.druid.pool.DruidDataSource;
import com.zw.oauth.config.CustomerPasswordEncode;
import com.zw.oauth.config.CustomerUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;


/**
 * oauth2 授权服务
 *
 * @author zw
 * @date 2020/8/19
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {
    @Autowired
    TokenStore tokenStore;
    @Qualifier("tokenAccess")
    @Autowired
    AccessTokenConverter converter;
    @Autowired
    AuthenticationManager manager;
    @Autowired
    CustomerUserDetailService userDetailsService;
    @Resource
    DruidDataSource dataSource;


    /**
     * 授权配置
     *
     * @param security 安全配置器
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //允许form表单客户端认证,允许客户端使用client_id和client_secret获取token
        security.allowFormAuthenticationForClients()
                //通过验证返回token
                .checkTokenAccess("permitAll()")
                //获取token请求不拦截
                .tokenKeyAccess("permitAll()");
        super.configure(security);
    }


    /**
     * 客户端信息注入
     *
     * @param clients clients
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource).passwordEncoder(new CustomerPasswordEncode());
    }


    /**
     * 端点控制器
     *
     * @param endpoints  端点控制器
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)  {
        endpoints
                .tokenStore(tokenStore)
                .accessTokenConverter(converter)
                .userDetailsService(userDetailsService)
                .authenticationManager(manager);
    }

}
