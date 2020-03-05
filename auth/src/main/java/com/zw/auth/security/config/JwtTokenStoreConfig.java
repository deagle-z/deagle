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

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Create by Tianhaobing ON 2019/2/3
 */
@Configuration
public class JwtTokenStoreConfig {

    /**
     * Jwt token store token store.
     *
     * @return the token store
     */
    @Bean
    public TokenStore jwtTokenStore() {

        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * Jwt access token converter jwt access token converter.
     *
     * @return the jwt access token converter
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {

        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("cpms");
        return converter;
    }

    /**
     * Jwt token enhancer token enhancer.
     *
     * @return the token enhancer
     */
    @Bean
    @ConditionalOnBean(TokenEnhancer.class)
    public TokenEnhancer jwtTokenEnhancer() {

        return new TokenJwtEnhancer();
    }

}
