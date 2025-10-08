package com.neu.system.ai.config;

import io.github.ollama4j.OllamaAPI;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OllamaConfig {
    @Value("${ollama.url}")
    private String ollamaUrl;

    @Value("${ollama.request-timeout-seconds:120}")
    private int ollamaRequestTimeoutSeconds;

    @Getter
    @Value("${ollama.model-name:deepseek-r1:1.5b}")
    private String modelName;

    @Bean
    OllamaAPI getOllamaAPI() {
        OllamaAPI ollamaAPI = new OllamaAPI(ollamaUrl);
        ollamaAPI.setVerbose(false);
        ollamaAPI.setRequestTimeoutSeconds(ollamaRequestTimeoutSeconds);
        return ollamaAPI;
    }
}
