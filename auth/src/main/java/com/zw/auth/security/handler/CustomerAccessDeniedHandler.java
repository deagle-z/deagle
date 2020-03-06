

package com.zw.auth.security.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
  * 被拒绝访问处理的handler
  * @date 2020/3/6
  * @author zw
*/
@Slf4j
@Configuration
public class CustomerAccessDeniedHandler implements AccessDeniedHandler {
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
        result.put("code", 401);
        result.put("message", "无访问权限");
        final String json = objectMapper.writeValueAsString(result);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
    }
}