

package com.zw.constant;


public class SystemConstant {

    public static final String UNKNOWN = "unknown";
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";
    public static final String X_REAL_IP = "X-Real-IP";
    public static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
    public static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
    public static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
    public static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";
    public static final String LOCALHOST_IP = "127.0.0.1";
    public static final String LOCALHOST_IP_16 = "0:0:0:0:0:0:0:1";
    public static final int MAX_IP_LENGTH = 15;

    /**
     * 系统常量
     */
    public static final class Sys {

        private Sys() {
        }

        /**
         * 全局用户名
         */
        public static final String TOKEN_AUTH_DTO = "CURRENT_USER_DTO";

        /**
         * The constant MENU_ROOT.
         */
        public static final String MENU_ROOT = "root";
    }

    /**
     * The class Symbol.
     *
     * @author paascloud.net@gmail.com
     */
    public static final class Symbol {
        private Symbol() {
        }

        /**
         * The constant COMMA.
         */
        public static final String COMMA = ",";
        public static final String SPOT = ".";
    }
}
