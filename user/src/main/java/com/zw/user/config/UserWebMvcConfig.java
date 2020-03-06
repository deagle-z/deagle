package com.zw.user.config;

import com.zw.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@EnableWebMvc
public class UserWebMvcConfig implements WebMvcConfigurer {

    @Resource
    private TokenInterceptor vueViewInterceptor;

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        //配置内容裁决的一些选项
    }



    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        //默认静态资源处理器
    }



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器   需要一个实现HandlerInterceptor的实例
        registry.addInterceptor(vueViewInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-ui.html", "/webjars/**",
                        "/v2/api-docs", "/swagger-resources/**", "*.js", "/**/*.js", "*.css", "/**/*.css", "*.html", "/**/*.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //自定义静态资源映射目录
//        registry.addResourceHandler("/**")
        // addResoureHandler : 指的是对外暴露的访问路径
        //addResourceLocations：指的是内部文件放置的目录
        //如果继承 WebMvcConfigurationSupport 则 ,必须重写这个方法
//                .addResourceLocations("classpath:/META-INF/resources/",
//                "classpath:/resources/", "classpath:/static/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //解决跨域问题
//        super.addCorsMappings(registry);
//        registry.addMapping("/cors/**")
//                .allowedHeaders("*")
//                .allowedMethods("POST","GET")
//                .allowedOrigins("*");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //视图跳转控制器  用于写jsp时的页面跳转 基本用不到现在
        // registry.addViewController("/toLogin").setViewName("login");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        //配置视图解析器 多用于jsp
    }



    /**
     * 消息内容转换配置
     * 配置fastJson返回json转换
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        //信息转换器
//        //创建fastJson消息转换器
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//        //创建配置类
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        //修改配置返回内容的过滤
//        fastJsonConfig.setSerializerFeatures(
//                SerializerFeature.DisableCircularReferenceDetect,
//                SerializerFeature.WriteMapNullValue,
//                SerializerFeature.WriteNullStringAsEmpty
//        );
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//        //将fastjson添加到视图消息转换器列表内
//        converters.add(fastConverter);
    }


}
