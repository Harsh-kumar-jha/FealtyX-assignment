package com.FealtyX_assignment.Student.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public OpenAPI studentOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Student Management API")
                        .description("API for managing student information with Ollama integration")
                        .version("1.0"));
    }
}
