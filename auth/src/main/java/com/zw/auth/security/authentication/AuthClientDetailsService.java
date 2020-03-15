package com.zw.auth.security.authentication;

import com.zw.security.properties.OAuth2ClientProperties;
import com.zw.security.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
  * 获取客户端信息
  * @date 2020/3/5
  * @author zw
*/

@Component
@Slf4j
public class AuthClientDetailsService implements ClientDetailsService {
    private ClientDetailsService clientDetailsService;

    @Resource
    private SecurityProperties securityProperties;

    /**
     * Init.
     * 将客户端的信息从配置文件中读出来 存储到内存中去
     */
    @PostConstruct
    public void init() {

        final InMemoryClientDetailsServiceBuilder builder = new InMemoryClientDetailsServiceBuilder();
        if (ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())) {
            for (final OAuth2ClientProperties client : securityProperties.getOauth2().getClients()) {
                builder.withClient(client.getClientId()) //clientId
                        .secret(client.getClientSecret()) //client密码
                        //.secret(passwordEncoder.encode(client.getClientSecret()))
                        .authorizedGrantTypes("refresh_token", "password", "client_credentials") //客户端的授权类型authorized_code : 授权码（短信验证码）方式
                        .accessTokenValiditySeconds(client.getAccessTokenValidateSeconds()) //token有效期
                        .refreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds()) //刷新token使劲按
                        .scopes(client.getScope()); //范围
            }
        }
        try {
            clientDetailsService = builder.build();
        } catch (final Exception e) {
            AuthClientDetailsService.log.error("init={}", e.getMessage(), e);
        }
    }

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        return clientDetailsService.loadClientByClientId(s);
    }
}
