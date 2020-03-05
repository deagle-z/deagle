/*
 * COPYRIGHT China Mobile (SuZhou) Software Technology Co.,Ltd. 2019
 *
 * The copyright to the computer program(s) herein is the property of
 * CMSS Co.,Ltd. The programs may be used and/or copied only with written
 * permission from CMSS Co.,Ltd. or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which the program(s) have been
 * supplied.
 */

package com.chinamobile.cmss.cpms.common.security.interceptor;

import com.chinamobile.cmss.cpms.common.base.constant.GlobalSecurityConstant;
import com.chinamobile.cmss.cpms.common.security.annotation.NoNeedAuthentication;
import com.chinamobile.cmss.cpms.common.security.dto.LoginAuthDto;
import com.chinamobile.cmss.cpms.common.utils.ThreadLocalMap;
import com.chinamobile.cmss.cpms.common.utils.redis.RedisKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Create by Tianhaobing ON 2019/2/11
 */
@Slf4j
@Component
@SuppressWarnings("PMD.CyclomaticComplexity")
public class TokenInterceptor implements HandlerInterceptor {

    @Value("${cpms.oauth2.jwtSigningKey}")
    private String jwtSigningKey;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static final String OPTIONS = "OPTIONS";

    private static final String AUTH_PATH1 = "/auth";

    private static final String AUTH_PATH2 = "/oauth";

    private static final String AUTH_PATH3 = "/error";

    private static final String AUTH_PATH4 = "/api";

    private static final String AUTH_PATH5 = "/user/register";

    private static final String AUTH_PATH6 = "/pendingRedirect";

    private static final String AUTH_PATH7 = "/sys/invest/health";

    /**
     * After completion.
     *
     * @param request  the request
     * @param response the response
     * @param arg2     the arg 2
     * @param ex       the ex
     * @throws Exception the exception
     */
    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object arg2,
                                final Exception ex) throws Exception {

        if (ex != null) {
            log.error("<== afterCompletion - 解析token失败. ex={}", ex.getMessage(), ex);
            this.handleException(response);
        }
    }

    /**
     * Post handle.
     *
     * @param request  the request
     * @param response the response
     * @param arg2     the arg 2
     * @param mv       the mv
     */
    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object arg2,
                           final ModelAndView mv) {

    }

    /**
     * Pre handle boolean.
     *
     * @param request  the request
     * @param response the response
     * @param handler  the handler
     * @return the boolean
     */
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {

        final String uri = request.getRequestURI();
        log.info("<== preHandle - 权限拦截器.  url={}", uri);
        if (uri.contains(AUTH_PATH1) || uri.contains(AUTH_PATH2) || uri.contains(AUTH_PATH3) || uri.contains(AUTH_PATH4)
                || uri.contains(AUTH_PATH5) || uri.contains(AUTH_PATH6) || uri.contains(AUTH_PATH7)) {
            log.info("<== preHandle - 配置URL不走认证.  url={}", uri);
            return true;
        }

        if (OPTIONS.equalsIgnoreCase(request.getMethod())) {
            log.info("<== preHandle - 调试模式不走认证.  url={}", uri);
            return true;
        }

        if (this.isHaveAccess(handler)) {
            log.info("<== preHandle - 不需要认证注解不走认证.  token={}");
            return true;
        }

        final String token = StringUtils.substringAfter(request.getHeader(HttpHeaders.AUTHORIZATION), "Bearer ");
        // log.info("<== preHandle - 权限拦截器. token={}", token);
        final LoginAuthDto loginUser = (LoginAuthDto) this.redisTemplate.opsForValue()
                .get(RedisKeyUtil.getAccessTokenKey(token));
        if (loginUser == null) {
            log.error("获取用户信息失败, 不允许操作");
            return false;
        }
        log.info("<== preHandle - 权限拦截器.  loginUser={}", loginUser.getUserName());
        ThreadLocalMap.put(GlobalSecurityConstant.TOKEN_AUTH_DTO, loginUser);
        return true;
    }

    private void handleException(final HttpServletResponse res) throws IOException {

        res.resetBuffer();
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write("{\"code\":100009 ,\"message\" :\"解析token失败\"}");
        res.flushBuffer();
    }

    private boolean isHaveAccess(final Object handler) {

        final HandlerMethod handlerMethod = (HandlerMethod) handler;

        final Method method = handlerMethod.getMethod();

        final NoNeedAuthentication responseBody = AnnotationUtils.findAnnotation(method, NoNeedAuthentication.class);
        return responseBody != null;
    }
}
