/*
 * COPYRIGHT China Mobile (SuZhou) Software Technology Co.,Ltd. 2019
 *
 * The copyright to the computer program(s) herein is the property of
 * CMSS Co.,Ltd. The programs may be used and/or copied only with written
 * permission from CMSS Co.,Ltd. or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which the program(s) have been
 * supplied.
 */
package com.chinamobile.cmss.cpms.common.auth.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;



/**
 * Create by Tianhaobing ON 2019/2/19
 */
@Slf4j
public class CpmsAuthLogoutSuccessHandler implements LogoutSuccessHandler {

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
