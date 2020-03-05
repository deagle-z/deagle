/*
 * COPYRIGHT China Mobile (SuZhou) Software Technology Co.,Ltd. 2019
 *
 * The copyright to the computer program(s) herein is the property of
 * CMSS Co.,Ltd. The programs may be used and/or copied only with written
 * permission from CMSS Co.,Ltd. or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which the program(s) have been
 * supplied.
 */

package com.chinamobile.cmss.cpms.common.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Create by Tianhaobing ON 2019/2/3
 */
@Component
@ConfigurationProperties(prefix = "cpms.security")
public class SecurityProperties {

    /**
     * 浏览器环境配置
     */
    private BrowserProperties browser = new BrowserProperties();

    /**
     * 验证码配置
     */
    // private ValidateCodeProperties code = new ValidateCodeProperties();

    /**
     * OAuth2认证服务器配置
     */
    private OAuth2Properties oauth2 = new OAuth2Properties();

    /**
     * Gets browser.
     *
     * @return the browser
     */
    public BrowserProperties getBrowser() {

        return browser;
    }

    /**
     * Sets browser.
     *
     * @param browser the browser
     */
    public void setBrowser(final BrowserProperties browser) {

        this.browser = browser;
    }

    /**
     * Gets oauth 2.
     *
     * @return the oauth 2
     */
    public OAuth2Properties getOauth2() {

        return oauth2;
    }

    /**
     * Sets oauth 2.
     *
     * @param oauth2 the oauth 2
     */
    public void setOauth2(final OAuth2Properties oauth2) {

        this.oauth2 = oauth2;
    }
}
