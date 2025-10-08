package com.neu.web.core.config;

import com.neu.common.annotation.Excel;
import com.neu.common.config.NeuConfig;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.lang.reflect.Field;
import java.util.Map;


/**
 * Swagger3的接口配置
 *
 * @author neu
 */
@Configuration
@Slf4j
public class SwaggerConfig {
    /**
     * 系统基础配置
     */
    @Autowired
    private NeuConfig neuConfig;

    /**
     * 自定义的 OpenAPI 对象
     */
    @Bean
    public OpenAPI customOpenApi() {
        OpenAPI openAPI = new OpenAPI().components(new Components()
                        // 设置认证的请求头
                        .addSecuritySchemes("apikey", securityScheme()))
                .addSecurityItem(new SecurityRequirement().addList("apikey"))
                .info(getApiInfo());
        return openAPI;
    }

    @Bean
    public SecurityScheme securityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .name("Authorization")
                .in(SecurityScheme.In.HEADER)
                .scheme("Bearer");
    }

    /**
     * 添加摘要信息
     */
    public Info getApiInfo() {
        return new Info()
                // 设置标题
                .title("标题：" + neuConfig.getName() + "接口文档")
                // 描述
                .description("描述：后端服务API接口文档.")
                // 作者信息
//                .contact(new Contact().name(neuConfig.getName()))
                // 版本
                .version("版本号:" + neuConfig.getVersion());
    }
}
