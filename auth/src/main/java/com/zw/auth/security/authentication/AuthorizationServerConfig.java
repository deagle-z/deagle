package com.zw.auth.security.authentication;

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
import java.util.ArrayList;
import java.util.List;

/**
  * 验证服务
  * @date 2020/3/5
  * @author zw
  * order 0
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
     * @param security the 用来配置令牌端点(Token Endpoint)的安全约束.
     *
     * @throws Exception the exception
     */
    @Override
    public void configure(final AuthorizationServerSecurityConfigurer security) throws Exception {
        // 开启/oauth/token_key验证端口无权限访问
        security.tokenKeyAccess("permitAll()"); //允许所有人可以访问获取token接口
        // 开启/oauth/check_token验证端口认证权限访问
        security.allowFormAuthenticationForClients();
    }

    /**
     * 用来配置客户端详情服务（ClientDetailsService），
     * 客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。.
     * client: 第三方应用(其他服务)
     * @param clients the clients
     *
     * @throws Exception the exception
     */
    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
//        super.configure();
//        clients.inMemory()
        clients.withClientDetails(authClientDetailsService);
    }

    /**
     * token存储
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services).
     *
     * @param endpoints the endpoints
     *
     * @throws Exception the exception
     */
    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints)  {
        //使用的密码模式，则必须有验证器 authenticationManager
        endpoints.tokenStore(jwtTokenStore)
                .authenticationManager(authenticationManager)  //密码验证管理器
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
