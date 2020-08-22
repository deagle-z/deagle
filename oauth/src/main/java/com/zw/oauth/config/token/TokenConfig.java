package com.zw.oauth.config.token;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class TokenConfig {

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(tokenAccess());
    }


    @Bean
    public JwtAccessTokenConverter tokenAccess(){
        final JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey("deagle");
        return tokenConverter;
    }



}
