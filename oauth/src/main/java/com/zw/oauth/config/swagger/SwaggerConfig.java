package com.zw.oauth.config.swagger;


import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger config
 *
 * @author zw
 * @date 2020/8/27
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
    * 排除不显示接口数组
    */
    public static final String[] EXCLUDED_PATH_PREFIX = {"authorize"};

    @Bean
    public Docket customerDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //定义扫描的controller
                .apis(RequestHandlerSelectors.basePackage("com.zw.oauth.business.controller"))
                .paths(data->{
                    for (String s : EXCLUDED_PATH_PREFIX) {
                        if(StringUtils.startsWith(data,s)){
                            return false;
                        }
                    }
                    return true;
                })
                .build();
    }



    private ApiInfo apiInfo() {
        Contact contact = new Contact("zw", "http", "xxx");
        return new ApiInfoBuilder()
                .title("auth模块相关接口")
                .contact(contact)
                .version("0.0.1")
                .build();
    }
}
