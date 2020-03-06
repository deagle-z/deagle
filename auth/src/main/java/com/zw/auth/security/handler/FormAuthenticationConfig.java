

package com.zw.auth.security.handler;

import com.zw.constant.SecurityConstants;
import com.zw.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


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
     * @param authenticationSuccessHandler the authentication success handler
     * @param cpmsAuthenticationFailureHandler the authentication failure handler
     */
    @Autowired
    public FormAuthenticationConfig(
            final AuthenticationSuccessHandler authenticationSuccessHandler, final AuthenticationFailureHandler cpmsAuthenticationFailureHandler) {
        this.cpmsAuthenticationSuccessHandler = authenticationSuccessHandler;
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
            throw new BusinessException(e.getMessage());
        }
    }
}
