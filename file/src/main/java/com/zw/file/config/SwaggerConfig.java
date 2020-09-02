package com.zw.file.config;


import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置类
 *
 * @author zw562
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).paths(PathSelectors.any())
                .build()
                // 配置鉴权信息
                // .securitySchemes(securitySchemes())
                // .securityContexts(securityContexts())
                // .globalOperationParameters(pars)
                .enable(true);
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("文件服务").description("文件服务")
                // .contact(new Contact(swagger.getContactName(),
                // swagger.getContactUrl(), swagger.getContactEmail()))
                .build();
    }

    // private List<ApiKey> securitySchemes() {
    //     return new ArrayList(Collections.singleton(new ApiKey("Authorization", "Authorization", "header")));
    // }
    //
    // private List<SecurityContext> securityContexts() {
    //     return new ArrayList(Collections.singleton(SecurityContext.builder().securityReferences(defaultAuth())
    //             .forPaths(PathSelectors.regex("^(?!auth).*$")).build()));
    // }

    // private List<SecurityReference> defaultAuth() {
    //     final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    //     final AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    //     authorizationScopes[0] = authorizationScope;
    //     return new ArrayList(Collections.singleton(new SecurityReference("Authorization", authorizationScopes)));
    // }
}
