package com.ceos20.spring_instagram.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("spring-instagram")
                .pathsToMatch("/api/**", "/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**")  // API와 Swagger UI 경로만 허용
                .build();
    }
}
