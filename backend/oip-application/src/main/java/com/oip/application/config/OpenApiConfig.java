package com.oip.application.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI oipOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Open Intelligence Platform API")
                        .version("v1")
                        .description("Memory First Foundation API for Open Intelligence Platform")
                        .license(new License().name("Apache-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Open Intelligence Platform documentation")
                        .url("https://github.com/open-intelligence-platform/oip"));
    }
}
