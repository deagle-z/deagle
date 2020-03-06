/*
 * COPYRIGHT China Mobile (SuZhou) Software Technology Co.,Ltd. 2019
 *
 * The copyright to the computer program(s) herein is the property of
 * CMSS Co.,Ltd. The programs may be used and/or copied only with written
 * permission from CMSS Co.,Ltd. or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which the program(s) have been
 * supplied.
 */

package com.chinamobile.cmss.cpms.common.utils.http;

import com.chinamobile.cmss.cpms.common.base.constant.GlobalSecurityConstant;
import com.chinamobile.cmss.cpms.common.base.constant.SystemConstant;
import com.chinamobile.cmss.cpms.common.base.exception.BusinessException;
import com.chinamobile.cmss.cpms.common.security.dto.LoginAuthDto;
import com.chinamobile.cmss.cpms.common.utils.ThreadLocalMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpRequestUtil {

    /**
     * Gets request.
     *
     * @return the request
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * Gets login user.
     *
     * @return the login user
     */
    public static LoginAuthDto getLoginUser() {
        final LoginAuthDto loginAuthDto = (LoginAuthDto) ThreadLocalMap.get(GlobalSecurityConstant.TOKEN_AUTH_DTO);
        if (loginAuthDto == null) {
            throw new BusinessException();
        }
        return loginAuthDto;

    }

    /**
     * Gets auth header.
     *
     * @param request the request
     * @return the auth header
     */
    public static String getAuthHeader(final HttpServletRequest request) {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(authHeader)) {
            throw new BusinessException();
        }
        return authHeader;
    }

    public static String[] extractAndDecodeHeader(final String header) throws IOException {

        final byte[] base64Token = header.substring(6).getBytes("UTF-8");
        final byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        } catch (final IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        final String token = new String(decoded, "UTF-8");

        final int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }

    /**
     * 获得用户远程地址
     *
     * @param request the request
     * @return the string
     */
    public static String getRemoteAddr(final HttpServletRequest request) {
        String ipAddress = request.getHeader(SystemConstant.X_REAL_IP);
       /*

        if (StringUtils.isEmpty(ipAddress) || GlobalConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(GlobalConstant.X_FORWARDED_FOR);
        }
        if (StringUtils.isEmpty(ipAddress) || GlobalConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(GlobalConstant.PROXY_CLIENT_IP);
        }
        if (StringUtils.isEmpty(ipAddress) || GlobalConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(GlobalConstant.WL_PROXY_CLIENT_IP);
        }
        if (StringUtils.isEmpty(ipAddress) || GlobalConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(GlobalConstant.HTTP_CLIENT_IP);
        }
        if (StringUtils.isEmpty(ipAddress) || GlobalConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(GlobalConstant.HTTP_X_FORWARDED_FOR);
        }
        if (StringUtils.isEmpty(ipAddress) || GlobalConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }*/
        if (org.springframework.util.StringUtils.isEmpty(ipAddress) || SystemConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (SystemConstant.LOCALHOST_IP.equals(ipAddress) || SystemConstant.LOCALHOST_IP_16.equals(ipAddress)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (final UnknownHostException e) {
                    log.error("获取IP地址, 出现异常={}", e.getMessage(), e);
                }
                assert inet != null;
                ipAddress = inet.getHostAddress();
            }
            log.info("获取IP地址 ipAddress={}", ipAddress);
        }
        // 对于通过多个代理的情况, 第一个IP为客户端真实IP,多个IP按照','分割 //"***.***.***.***".length() = 15
        if (ipAddress != null && ipAddress.length() > SystemConstant.MAX_IP_LENGTH) {
            if (ipAddress.indexOf(SystemConstant.Symbol.COMMA) > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(SystemConstant.Symbol.COMMA));
            }
        }
        return ipAddress;
    }
}
