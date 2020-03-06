package com.zw.auth.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zw.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认的退出成功处理器，如果设置了paascloud.security.browser.signOutUrl，则跳到配置的地址上，
 * 如果没配置，则返回json格式的响应。
 * Created by Tian Haobing on 2019/6/23
 */
@Slf4j
public class ClientLogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * On logout success.
     *
     * @param request        the request
     * @param response       the response
     * @param authentication the authentication
     * @throws IOException the io exception
     */
    @Override
    public void onLogoutSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication)
            throws IOException {
        log.info("退出成功");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(this.objectMapper.writeValueAsString(R.success()));
    }

}
