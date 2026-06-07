package com.oip.application;

import com.oip.infrastructure.provider.OllamaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = "com.oip")
@EnableConfigurationProperties(OllamaProperties.class)
public class OipApplication {

    public static void main(String[] args) {
        SpringApplication.run(OipApplication.class, args);
    }
}
