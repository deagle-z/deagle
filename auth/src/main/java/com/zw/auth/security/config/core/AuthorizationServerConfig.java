package com.zw.auth.security.config.core;

import com.zw.auth.security.authentication.AuthClientDetailsService;
import com.zw.auth.security.handler.AuthLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
  * EnableAuthorizationServer 开启授权服务
  * 授权服务基础配置类
  *     authClientDetailsService 配置访问客户端
  *
  * 授权 返回token
  * order 0
  * oauth2 核心类 核心功能
  * @date 2020/3/5
  * @author zw
*/
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Resource
    private TokenStore jwtTokenStore;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private AuthClientDetailsService authClientDetailsService;

    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;


    /**
     * 那些url可以匿名访问.
     *
     * @param security 用来配置令牌端点(Token Endpoint)的安全约束.
     *
     */
    @Override
    public void configure(final AuthorizationServerSecurityConfigurer security) {
        System.out.println("===================> AuthorizationServerConfig AuthorizationServerSecurityConfigurer config  <==================="+ LocalDateTime.now());
        // 开启/oauth/token_key验证端口无权限访问
        //允许所有人可以访问获取token接口
        security.tokenKeyAccess("permitAll()");
        // 开启/oauth/check_token验证端口认证权限访问
        security.allowFormAuthenticationForClients();
    }

    /**
     * 用来配置客户端详情服务（ClientDetailsService 其它微服务），
     * 客户端详情信息在这里进行初始化，能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
     * client: 第三方应用(其他服务)
     * @param clients the clients
     *
     * @throws Exception the exception
     */
    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        System.out.println("===================> AuthorizationServerConfig ClientDetailsServiceConfigurer config  <===================" + LocalDateTime.now());
        clients.withClientDetails(authClientDetailsService);
    }

    /**
     * token存储
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services).
     * 令牌的访问端点
     * @param endpoints the endpoints
     *
     * @throws Exception the exception
     */
    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints)  {
        //使用的密码模式，则必须有验证器 authenticationManager
        System.out.println("===================> AuthorizationServerConfig AuthorizationServerEndpointsConfigurer config  <===================" + LocalDateTime.now());
        endpoints.tokenStore(jwtTokenStore)
                //authenticationManager 密码验证的认证管理器，密码模式必备
                .authenticationManager(authenticationManager)
                //获取用户详细信息 refreshToken 校验用户
                .userDetailsService(userDetailsService);

        if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null) {
            final TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
            final List<TokenEnhancer> enhancers = new ArrayList<>();
            enhancers.add(jwtTokenEnhancer);
            enhancers.add(jwtAccessTokenConverter);
            enhancerChain.setTokenEnhancers(enhancers);
            endpoints.tokenEnhancer(enhancerChain).accessTokenConverter(jwtAccessTokenConverter);
        }
    }

    /**
     * 退出时的处理策略配置
     *
     * @return logout success handler
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {

        return new AuthLogoutSuccessHandler();
    }
}
