package com.zw.zuul.filter;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenFilter extends ZuulFilter {

    @Override
    public String filterType() {
        //前置过滤器
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //设置filter Order
        return 0;
    }

    /**
     * 判断此过滤器是否执行
     *
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 具体的业务逻辑
     */
    @Override
    public Object run() throws ZuulException {
        final RequestContext context = RequestContext.getCurrentContext();
        final HttpServletRequest request = context.getRequest();
        String authorization = request.getHeader("Authorization");
        if (authorization != null && !authorization.isEmpty()) {
            context.addZuulRequestHeader("Authorization", authorization);
        }
        return null;
    }
}
