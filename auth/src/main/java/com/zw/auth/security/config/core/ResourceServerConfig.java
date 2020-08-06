

package com.zw.auth.security.config.core;


import com.zw.auth.security.handler.FormAuthenticationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * 校验权限
 * 资源服务配置 / 接口访问权限配置
 * 资源处理器 同时有WebSecurityConfigurerAdapter与ResourceServerConfigurerAdapter同时在的话且都配置了处理同一url,后者会生效
 * 因为默认的WebSecurityConfigurerAdapter里的@Order值是100（我们可以在该类上可以明确看到@Order(100)），而
 * 我们在ResourceServerConfigurerAdapter上添加了@EnableResourceServer注解，这个玩意儿是干什么用的呢？他其中之一就是定义了@Order值为3
 * （该注解里引用了ResourceServerConfiguration，这个类里面定义了Order值）.在spring 的体系里Order值越小优先级越高，所以ResourceServerConfigurerAdapter
 * 优先级比另外一个更高，他会优先处理，而WebSecurityConfigurerAdapter会失效
 *
 * 我们每声明一个*Adapter类，都会产生一个filterChain ,而一个request（匹配url）只能被一个filterChain处理
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;


    @Autowired
    private FormAuthenticationConfig formAuthenticationConfig;

    @Resource
    private DataSource dataSource;

    /**
     * 记住我功能的token存取器配置
     *
     * @return the persistent token repository
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {

        final JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(this.dataSource);
        //		tokenRepository.setCreateTableOnStartup(true); // 第一次启动创建
        return tokenRepository;
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        this.formAuthenticationConfig.configure(http);
        // 配置额外的过滤器 ResourceServerConfigurerAdapter
//      http.addFilterAt(this.restAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().disable()
                .and()
                .exceptionHandling().accessDeniedHandler(this.accessDeniedHandler)
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/druid/**").permitAll()
                .antMatchers("/user/register", "/v2/api-docs", "/configuration/ui", "/swagger-resources",
                        "/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui",
                        "/swagge‌​r-ui.html", "/uniauth/api_v/oauth/login").permitAll()
                .anyRequest().authenticated();
        //antMatcher()``是HttpSecurity的一个方法，他只告诉了Spring我只配置了一个我这个Adapter能处理哪个的url
        //authorizeRequests().antMatchers()是告诉你在antMatchers()中指定的一个或多个路径,比如执行permitAll()或hasRole()。他们在第一个http.antMatcher()匹配时就会生效

    }

    /**
      * 资源服务验证token
      *
      * @param resources
      */
    @Override
    public void configure(final ResourceServerSecurityConfigurer resources) {
        //resources.tokenExtractor(new CpmsBearerTokenExtractor());
        resources.expressionHandler(this.oAuth2WebSecurityExpressionHandler);
    }

}
