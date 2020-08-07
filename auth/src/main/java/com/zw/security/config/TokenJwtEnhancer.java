package com.zw.security.config;

import com.zw.base.entity.SecurityDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
  * jwt token 增强
  *
  * @date 2020/3/5
  * @author zw
*/
public class TokenJwtEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken token, OAuth2Authentication oAuth2Authentication) {
        System.out.println("================>jwt token enhance<=============="+ LocalDateTime.now());
        final Map<String, Object> info = new HashMap<>(8);
        info.put("timestamp", System.currentTimeMillis());
        final Authentication authentication = oAuth2Authentication.getUserAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            final Object principal = authentication.getPrincipal();
            info.put("loginName", ((SecurityDetail) principal).getUsername());
            info.put("userId", ((SecurityDetail) principal).getId()+"");
        }
        ((DefaultOAuth2AccessToken) token).setAdditionalInformation(info);
        return token;
    }
}
