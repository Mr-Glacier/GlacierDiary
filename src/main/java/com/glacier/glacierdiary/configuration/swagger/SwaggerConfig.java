package com.glacier.glacierdiary.configuration.swagger;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Collections;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote knife4j 的 Swagger配置
 * @since 2025/1/23 14:27
 */
@Configuration
@EnableSwagger2WebMvc
@EnableKnife4j
@ConditionalOnProperty(name = "knife4j.enable", matchIfMissing = true)
public class SwaggerConfig {

    /**
     * 系统服务 (包含 验证码,用户登录,用户注册)
     */
    @Bean(value = "system API")
    public Docket systemApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("System Services")
                        .description("系统服务")
                        .contact(new Contact("Mr-Glacier", "https://github.com/Mr-Glacier", "mrglacier@yeah.net"))
                        .license("Apache License 2.0")
                        .version("1.0")
                        .build())
                .groupName("系统服务")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.glacier.glacierdiary.controller.system")).build();

    }

    /**
     * 管理服务 (包含 角色管理,用户管理,路由管理等)
     */
    @Bean(value = "admin API")
    public Docket adminApi() {
        // admin 服务 需要授权
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("Authorization").description("用户认证令牌")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("Admin Services")
                        .description("管理服务")
                        .contact(new Contact("Mr-Glacier", "https://github.com/Mr-Glacier", "mrglacier@yeah.net"))
                        .license("Apache License 2.0")
                        .version("1.0")
                        .build())
                .groupName("管理服务")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.glacier.glacierdiary.controller.admin")).build()
                .globalOperationParameters(Collections.singletonList(tokenPar.build()));

    }

    /**
     * 业务服务 (包含 其他业务接口)
     */
    @Bean(value = "general API")
    public Docket dockerBean() {
        // general 服务 需要授权
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("Authorization").description("用户认证令牌")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("General Services")
                        .description("业务服务")
                        .contact(new Contact("Mr-Glacier", "https://github.com/Mr-Glacier", "mrglacier@yeah.net"))
                        .license("Apache License 2.0")
                        .version("1.0")
                        .termsOfServiceUrl("https://github.com/Mr-Glacier")
                        .build())
                .groupName("业务服务")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.glacier.glacierdiary.controller.client")).build()
                .globalOperationParameters(Collections.singletonList(tokenPar.build()));
    }

}
