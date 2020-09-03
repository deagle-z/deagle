package com.zw.oauth.config.token;

import com.zw.oauth.business.pojo.dto.UserDetailsDto;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * token增强
 * 
 * @author zw
 * @date 2020/9/3 
 */
@Component
public class JwtTokenEnhancer implements TokenEnhancer {

	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication auth2Authentication) {
		final UserDetailsDto userDetailsDto = (UserDetailsDto) auth2Authentication.getPrincipal();
		Map<String, Object> map = new HashMap<>(16);
		map.put("name",userDetailsDto.getUsername());
		((DefaultOAuth2AccessToken)oAuth2AccessToken).setAdditionalInformation(map);
		return oAuth2AccessToken;
	}
}
