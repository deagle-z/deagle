/*
 * COPYRIGHT China Mobile (SuZhou) Software Technology Co.,Ltd. 2019
 *
 * The copyright to the computer program(s) herein is the property of
 * CMSS Co.,Ltd. The programs may be used and/or copied only with written
 * permission from CMSS Co.,Ltd. or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which the program(s) have been
 * supplied.
 */

package com.chinamobile.cmss.cpms.common.auth.config;

/**
 * Create by Tianhaobing ON 2019/3/26
 */
public class SecurityConstants {

    /**
     * 默认的处理验证码的url前缀
     */
    public static String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/auth/code";

    /**
     * 当请求需要身份认证时，默认跳转的url
     */
    public static String DEFAULT_UNAUTHENTICATION_URL = "/auth/require";
    /**
     * 默认的用户名密码登录请求处理url
     */
    public static String DEFAULT_SIGN_IN_PROCESSING_URL_FORM = "/auth/form";
    /**
     * 默认的手机验证码登录请求处理url
     */
    public static String DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE = "/auth/mobile";
    /**
     * 默认的OPENID登录请求处理url
     */
    public static String DEFAULT_SIGN_IN_PROCESSING_URL_OPENID = "/auth/openid";
    /**
     * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
    public static String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
    /**
     * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    public static String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";
    /**
     * 验证邮箱验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    public static String DEFAULT_PARAMETER_NAME_CODE_EMAIL = "emailCode";
    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    public static String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";

    /**
     * 发送邮箱验证码 或 验证邮箱验证码时，传递邮箱的参数的名称
     */
    public static String DEFAULT_PARAMETER_NAME_EMAIL = "email";

    /**
     * openid参数名
     */
    public static String DEFAULT_PARAMETER_NAME_OPENID = "openId";
    /**
     * providerId参数名
     */
    public static String DEFAULT_PARAMETER_NAME_PROVIDERID = "providerId";
    /**
     * 获取第三方用户信息的url
     */
    public static String DEFAULT_SOCIAL_USER_INFO_URL = "/social/user";
}
