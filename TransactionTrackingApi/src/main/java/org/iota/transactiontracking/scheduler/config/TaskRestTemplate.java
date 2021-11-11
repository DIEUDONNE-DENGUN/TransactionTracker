package org.iota.transactiontracking.scheduler.config;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
  @Description: generate a rest template beean for use to make external request to the restful api interface
 */
@Configuration
public class TaskRestTemplate {

    @Bean(name = "trackerRestTemplate")
    public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .errorHandler(new TaskRestTemplateResponseErrorHandler())
                .build();
    }
}
