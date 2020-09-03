package com.zw.provider.major.Interceptor;

import com.zw.util.ThreadLocalMap;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 *
 * @author zw562
 */
public class ThreadLocalInterceptor implements HandlerInterceptor {
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		final Object user = ThreadLocalMap.get("CURRENT_USER_DTO");
		if (null != user) {
			ThreadLocalMap.remove();
		}
	}
}
