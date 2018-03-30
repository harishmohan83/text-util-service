package org.text.util.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.text.util.documentation.Notes;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfiguration {

    @Bean
    public Docket customerApis() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).groupName("text-util-service").apiInfo(apiInfo())
                .select().apis(RequestHandlerSelectors.basePackage("org.text.util.controllers"))
                .paths(PathSelectors.regex("/*/.*")).build();
    }

    @SuppressWarnings("deprecation")
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Text Util Service Specifications").description(Notes.SERVICE_DESCRIPTION)
                .version("1.0").build();
    }
}