package com.zw.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.servlet.http.HttpServletResponse;

/**
  * 资源服务器设置
  * @date 2020/3/5
  * @author zw
*/
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//    @Autowired
//    TokenStore tokenStore;


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
//        资源安全配置相关
//        resources.resourceId().stateless().tokenStore(tokenStore).authenticationEntryPoint()
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http 安全配置 一般设置 排除一些不需要权限的url 过滤器
        super.configure(http);
        http.headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .exceptionHandling() //异常处理器
//                .accessDeniedHandler()
//                .and().addFilterBefore() 添加过滤器
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .antMatchers("/druid/**", "/user/register", "/configuration/ui", "/swagger-resources", "/configuration/security",
                        "/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui", "/swagge‌​r-ui.html").permitAll()
                .anyRequest().authenticated();
    }
}
