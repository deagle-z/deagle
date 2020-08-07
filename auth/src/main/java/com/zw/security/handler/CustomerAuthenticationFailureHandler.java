

package com.zw.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zw.response.R;
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


@Component("cpmsAuthenticationFailureHandler")
public class CustomerAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

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
            response.getWriter().write(objectMapper.writeValueAsString(new R(500,"登录失败")));
        }else {
            response.getWriter().write(objectMapper.writeValueAsString(new R(500,"权限认证失败")));
        }

    }

}