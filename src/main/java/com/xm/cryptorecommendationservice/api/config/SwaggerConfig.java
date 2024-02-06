package com.xm.cryptorecommendationservice.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    @Primary
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return () -> {
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setName("Crypto Recommendation API");
            swaggerResource.setSwaggerVersion("3.0");
            swaggerResource.setLocation("/doc/crypto-recommendation-swagger.yaml");
            return List.of(swaggerResource);
        };
    }
}
