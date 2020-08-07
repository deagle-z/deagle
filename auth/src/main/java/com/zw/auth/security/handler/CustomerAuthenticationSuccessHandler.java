

package com.zw.auth.security.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zw.base.dto.LoginAuthDto;
import com.zw.base.entity.SecurityDetail;
import com.zw.constant.RedisKeyConstant;
import com.zw.response.R;
import com.zw.auth.security.properties.OAuth2ClientProperties;
import com.zw.auth.security.properties.SecurityProperties;
import com.zw.util.HttpRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * 登陆成功处理器
 */
@Slf4j
@Component("CustomerAuthenticationSuccessHandler")
public class CustomerAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private ClientDetailsService clientDetailsService;

    @Resource
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    private static final String BEARER_TOKEN_TYPE = "Basic ";

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) throws IOException {
        log.info("-----登录成功-------登陆成功处理器");
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith(BEARER_TOKEN_TYPE)) {
            throw new UnapprovedClientAuthenticationException("请求头中无client信息");
        }

        final String[] tokens = HttpRequestUtil.extractAndDecodeHeader(header);
        assert tokens.length == 2;
        final String clientId = tokens[0];
        final String clientSecret = tokens[1];
        final ClientDetails clientDetails = this.clientDetailsService.loadClientByClientId(clientId);
        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
        } else if (!this.passwordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
        }
        final TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");

        final OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        final OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        final OAuth2AccessToken token = this.authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        final SecurityDetail principal = (SecurityDetail) authentication.getPrincipal();
        final LoginAuthDto authDto = new LoginAuthDto();
        final OAuth2ClientProperties[] clients = this.securityProperties.getOauth2().getClients();
        final int accessTokenValidateSeconds = clients[0].getAccessTokenValidateSeconds();

        this.redisTemplate.opsForValue().set(RedisKeyConstant.TOKEN_KEY+token.getValue(), authDto, accessTokenValidateSeconds, TimeUnit.SECONDS);
        log.info("用户【 {} 】记录登录日志", principal.getUsername());

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write((this.objectMapper.writeValueAsString(R.success(token))));

    }

//    private List<LoginMenuDto> setMenusDto(final List<LoginMenuDto> menuDtos) {
//        //多级菜单
//        List<LoginMenuDto> firstLevel = menuDtos.stream().filter(m -> m.getParentId().equals(0L)).filter(m -> m.getDeletedFlag() == 0).collect(Collectors.toList());
//        if (!CollectionUtils.isEmpty(firstLevel)) {
//
//            firstLevel = new ArrayList<>(new LinkedHashSet<>(firstLevel));
//            firstLevel.parallelStream().forEach(p -> {
//                this.setChild(p, menuDtos);
//            });
//        }
//        return firstLevel;
//    }
//
//    private void setChild(final LoginMenuDto p, final List<LoginMenuDto> menuDtos) {
//        final List<LoginMenuDto> child = menuDtos.parallelStream().filter(a -> a.getParentId().equals(p.getMenuId())).collect(Collectors.toList());
//        p.setChild(child);
//        if (!CollectionUtils.isEmpty(child)) {
//            child.parallelStream().forEach(c -> {
//                //递归子元素
//                this.setChild(c, menuDtos);
//            });
//        }
//    }

}