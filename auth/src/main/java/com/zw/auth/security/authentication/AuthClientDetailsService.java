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
     */
    @PostConstruct
    public void init() {

        final InMemoryClientDetailsServiceBuilder builder = new InMemoryClientDetailsServiceBuilder();
        if (ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())) {
            for (final OAuth2ClientProperties client : securityProperties.getOauth2().getClients()) {
                builder.withClient(client.getClientId())
                        .secret(client.getClientSecret())
                        //.secret(passwordEncoder.encode(client.getClientSecret()))
                        .authorizedGrantTypes("refresh_token", "password", "client_credentials")
                        .accessTokenValiditySeconds(client.getAccessTokenValidateSeconds())
                        .refreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds()).scopes(client.getScope());
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
