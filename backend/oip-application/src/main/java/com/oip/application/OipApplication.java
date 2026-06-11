package com.oip.application;

import com.oip.infrastructure.instruction.OipPersonalityProperties;
import com.oip.infrastructure.provider.OllamaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.oip")
@EnableConfigurationProperties({OllamaProperties.class, OipPersonalityProperties.class})
@EntityScan(basePackages = "com.oip.domain")
@EnableJpaRepositories(basePackages = "com.oip.infrastructure")
public class OipApplication {

    public static void main(String[] args) {
        SpringApplication.run(OipApplication.class, args);
    }
}
