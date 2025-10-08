package com.neu.web.core.config;

import com.alibaba.fastjson.JSON;
import com.neu.common.annotation.Excel;
import com.neu.common.utils.StringUtils;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;

@Component
@Slf4j
public class CustomizedOpenApiCustomizer implements GlobalOpenApiCustomizer {
    @Override
    public void customise(OpenAPI openApi) {
        Components components = openApi.getComponents();
        if (components != null) {
            Map<String, Schema> schemas = components.getSchemas();
//            log.info("遍历所有模型，schemas 大小: {}", schemas != null ? schemas.size() : 0);
            if (schemas != null) {
                for (Map.Entry<String, Schema> entry : schemas.entrySet()) {
//                    String className = entry.getKey();
                    Schema schema = entry.getValue();
//                    log.info("遍历所有模型: {}", schema.getTitle());
                    if(StringUtils.isBlank(schema.getTitle())){
                        continue;
                    }
                    try {
                        Class<?> clazz = Class.forName(schema.getTitle());
                        for (Field field : clazz.getDeclaredFields()) {
//                            log.info("遍历所有模型字段: {}", field.getName());
                            // 优先使用 @Schema 描述
                            if (field.isAnnotationPresent(io.swagger.v3.oas.annotations.media.Schema.class)) {
//                                log.info("优先使用 @Schema 描述");
                                io.swagger.v3.oas.annotations.media.Schema schemaAnno = field.getAnnotation(io.swagger.v3.oas.annotations.media.Schema.class);
                                Schema fieldSchema = (Schema) schema.getProperties().get(field.getName());
                                if (fieldSchema != null) {
                                    fieldSchema.description(schemaAnno.description());
                                }
                            } else if (field.isAnnotationPresent(Excel.class)) {
                                Excel excel = field.getAnnotation(Excel.class);
                                Schema fieldSchema = (Schema) schema.getProperties().get(field.getName());
//                                log.info("使用 @Excel 描述:{},fieldSchema:{}", field.getName(), fieldSchema);
                                if (fieldSchema != null) {
                                    fieldSchema.description(excel.name());
                                }
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        // 忽略无法加载的类
                    }
                }
            }
        }
    }
}
