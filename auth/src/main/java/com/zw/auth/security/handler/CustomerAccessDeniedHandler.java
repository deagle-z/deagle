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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * Create by Tianhaobing ON 2019/3/26
 */
@Slf4j
@Configuration
public class CpmsAccessDeniedHandler implements AccessDeniedHandler {
    @Resource
    private ObjectMapper objectMapper;

    /**
     * Handle.
     *
     * @param request  the request
     * @param response the response
     * @param e        the e
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response, final AccessDeniedException e) throws
            IOException {
        log.info("处理权限异常. e={}", e);
        final Map<String, Object> result = new HashMap<>(3);
        result.put("code", 99990401);
        result.put("message", "无访问权限");
        final String json = objectMapper.writeValueAsString(result);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
    }
}