
package com.zw.security.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthLogoutSuccessHandler implements LogoutSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * On logout success.
     *
     * @param request the request
     * @param response the response
     * @param authentication the authentication
     *
     * @throws IOException the io exception
     */
    @Override
    public void onLogoutSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                final Authentication authentication) throws IOException {

        log.info("退出成功");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString("退出成功"));
    }
}
