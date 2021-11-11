package org.iota.transactiontracking.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
  @Description: set swagger configurations
 */
@Configuration
@EnableSwagger2
@Profile("!prod")
public class SwaggerConfig {
    @Value("${application.name}")
    private String appName;
    @Value("${application.description}")
    private String appDescription;
    @Value("${application.version}")
    private String appVersion;

    @Bean
    public Docket transactionTrackerApi() {
        HashSet<String> consumesAndProduces =
                new HashSet<>(Arrays.asList("application/json"));

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getSwaggerMetaData())
                .consumes(consumesAndProduces)
                .produces(consumesAndProduces)
                .globalOperationParameters(
                        Arrays.asList(new ParameterBuilder()
                                .name("Authorization")
                                .defaultValue("ApiKey ")
                                .description("Api security key to grant access to all protected endpoints")
                                .modelRef(new ModelRef("string"))
                                .parameterType("header")
                                .required(true)
                                .build()))
                .select().apis(RequestHandlerSelectors.basePackage("org.iota.transactiontracking.api.controller"))
                .paths(PathSelectors.regex("/api.*"))
                .apis(RequestHandlerSelectors.any())
                .build();
    }

    private ApiInfo getSwaggerMetaData() {
        return new ApiInfoBuilder()
                .title(appName + " Restful Interface")
                .description(appDescription)
                .contact(new Contact("Dieudonne Takougang", "https://iota.org", "tracker@iota.org"))
                .version(appVersion)
                .build();
    }
}
