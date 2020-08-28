package com.zw.oauth.config.customer;

import com.zw.exception.BusinessException;
import com.zw.util.HttpRequestUtil;
import com.zw.util.LocalDateTimeUtil;
import com.zw.util.StringUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
//@Slf4j
public class CustomerAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        final String now = LocalDateTimeUtil.nowStr();
//        log.info(now+"=========>登录成功");
        final String authorizationToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtil.isEmpty(authorizationToken)){
            throw new BusinessException("非法请求");
        }
        //验证client
        final String[] tokens = HttpRequestUtil.extractAndDecodeHeader(authorizationToken);
        assert tokens.length == 2;

        final String clientId = tokens[0];
        final String clientSecret = tokens[1];


        //验证token
        authentication.getDetails();
    }
}
