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

import com.chinamobile.cmss.cpms.common.base.exception.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * Create by Tianhaobing ON 2019/3/26
 */
@Component
public class FormAuthenticationConfig {
    /**
     * The authentication success handler.
     */
    protected final AuthenticationSuccessHandler cpmsAuthenticationSuccessHandler;

    /**
     * The authentication failure handler.
     */
    protected final AuthenticationFailureHandler cpmsAuthenticationFailureHandler;

    /**
     * Instantiates a new Form authentication config.
     *
     * @param cpmsAuthenticationSuccessHandler the authentication success handler
     * @param cpmsAuthenticationFailureHandler the authentication failure handler
     */
    @Autowired
    public FormAuthenticationConfig(
            final AuthenticationSuccessHandler cpmsAuthenticationSuccessHandler, final AuthenticationFailureHandler cpmsAuthenticationFailureHandler) {
        this.cpmsAuthenticationSuccessHandler = cpmsAuthenticationSuccessHandler;
        this.cpmsAuthenticationFailureHandler = cpmsAuthenticationFailureHandler;
    }

    /**
     * Configure.
     *
     * @param http the http
     *
     * @throws Exception the exception
     */
    public void configure(final HttpSecurity http) {

        try {
            http.formLogin()
                    .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                    .loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
                    .successHandler(cpmsAuthenticationSuccessHandler)
                    .failureHandler(cpmsAuthenticationFailureHandler);
        } catch (final Exception e) {
            throw new SystemException(e.getMessage());
        }
    }
}
