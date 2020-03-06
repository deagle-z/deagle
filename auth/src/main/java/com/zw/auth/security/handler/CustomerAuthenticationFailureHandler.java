/*
 * COPYRIGHT China Mobile (SuZhou) Software Technology Co.,Ltd. 2019
 *
 * The copyright to the computer program(s) herein is the property of
 * CMSS Co.,Ltd. The programs may be used and/or copied only with written
 * permission from CMSS Co.,Ltd. or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which the program(s) have been
 * supplied.
 */

package com.zw.auth.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Create by Tianhaobing ON 2019/3/26
 */
@Component("cpmsAuthenticationFailureHandler")
public class CpmsAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Resource
    private ObjectMapper objectMapper;

    /**
     * On authentication failure.
     *
     * @param request   the request
     * @param response  the response
     * @param exception the exception
     *
     * @throws IOException      the io exception
     * @throws ServletException the servlet exception
     */
    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
                                        final AuthenticationException exception) throws IOException, ServletException {

        logger.info("登录失败");


        // 记录失败次数 和原因 ip等信息 5次登录失败,锁定用户, 不允许在此登录
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");
        if (exception instanceof InsufficientAuthenticationException){
            response.getWriter().write(objectMapper.writeValueAsString(ResponseUtil.wrapException(ExceptionEnum.USER_AUTH_CODE_ERROR.getCode(),
                    ExceptionEnum.USER_AUTH_CODE_ERROR.getMessage())));
        }else {
            response.getWriter().write(objectMapper.writeValueAsString(ResponseUtil.wrapException(ExceptionEnum.USER_AUTH_CREDENTIAL_ERROR.getCode(),
                    ExceptionEnum.USER_AUTH_CREDENTIAL_ERROR.getMessage())));
        }

    }

}