package com.zw.auth.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;


@Configuration
public class JwtTokenStoreConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
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
        converter.setSigningKey("deagle");
        // 可以设置证书
//        KeyStoreKeyFactory keyStoreKeyFactory =
//                new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "mypass".toCharArray());
//        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
//        通过 JDK 工具生成 JKS 证书文件，并将 keystore.jks 放入resource目录下
//        keytool -genkeypair -alias mytest -keyalg RSA -keypass mypass -keystore keystore.jks -storepass mypass
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

    @Bean
//    @ConditionalOnProperty(prefix = "whale.security.oauth2",name = "storeType",havingValue = "redis",matchIfMissing = false)
    public TokenStore tokenStore(){
        //prefix为配置文件中的前缀,
        //name为配置的名字
        //havingValue是与配置的值对比值,当两个值相同返回true,配置类生效.
        return new RedisTokenStore(redisConnectionFactory);
    }
}
