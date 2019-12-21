package com.zw.util;

import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
  * request 工具类
  * @date 2019/12/21
  * @author zw
*/
public class RequestUtils {
    @Value("${server.port}")
    private static   String serverPort ;
    /**
     * 获取request对象
     * @return request
     */
    public static HttpServletRequest getRequest() {
        try {
            return((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 项目的真实路径
     * @return String
     */
    public static String getPjoPath() {
        try {
            return // 项目的真实路径
                    StringUtils.replace(getRequest().getServletContext().getContextPath(), "/", "\\");
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 获取客户端请求的路径名，如：/object/delObject
     * @return String
     */
    public static String getServletPath() {
        try {
            return // 项目的真实路径
                    getRequest().getServletPath();
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 获取服务器地址，如：localhost
     * @return String
     */
    public static String getServerName() {
        try {
            return // 项目的真实路径
                    getRequest().getServerName();
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 获取服务器端口，如8080
     * @return String
     */
    public static String getServerPort() {
        try {
            return // 项目的真实路径
                    getRequest().getServerPort()+"";
        } catch (Exception e) {
            return serverPort;
        }
    }
    /**
     * 获用户地址，如：127.0.0.1
     * @return String
     */
    public static String getRemoteAddr() {
        try {
            String remoteAddr = getRequest().getHeader("X-Real-IP");
            if (StringUtil.isNotBlank(remoteAddr)) {
                remoteAddr = getRequest().getHeader("X-Forwarded-For");
            } else if (StringUtil.isNotBlank(remoteAddr)) {
                remoteAddr = getRequest().getHeader("Proxy-Client-IP");
            } else if (StringUtil.isNotBlank(remoteAddr)) {
                remoteAddr = getRequest().getHeader("WL-Proxy-Client-IP");
            }
            return remoteAddr != null ? remoteAddr : getRequest().getRemoteAddr();
        } catch (Exception e) {
            return "";
        }
    }
    /**
     * 获取项目的访问路径，如： localhost:8080/xx
     * @return String
     */
    public static String getObjUrl() {
        return getServerName()+":"+getServerPort()+getServletPath();
    }
}
