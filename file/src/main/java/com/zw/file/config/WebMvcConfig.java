package com.zw.file.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    // @Override
    // public void addInterceptors(final InterceptorRegistry registry) {
    //     registry.addInterceptor(vueViewInterceptor)
    //             .addPathPatterns("/**")
    //             .excludePathPatterns("/swagger-ui.html", "/webjars/**", "/v2/api-docs", "/swagger-resources/**", "*.js", "/**/*.js", "*.css", "/**/*.css", "*.html", "/**/*.html");
    // }


    // @Override
    // public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
    //     ObjectMapper.buidMvcMessageConverter(converters);
    // }

    // public static void buidMvcMessageConverter(final List<HttpMessageConverter<?>> converters) {
    //     final MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
    //     final SimpleModule simpleModule = new SimpleModule();
    //     simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
    //     simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
    //     final com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper()
    //             .registerModule(new ParameterNamesModule())
    //             .registerModule(new Jdk8Module())
    //             .registerModule(new JavaTimeModule())
    //             .registerModule(simpleModule);
    //     objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    //     jackson2HttpMessageConverter.setObjectMapper(objectMapper);
    //     converters.add(jackson2HttpMessageConverter);
    // }
}
