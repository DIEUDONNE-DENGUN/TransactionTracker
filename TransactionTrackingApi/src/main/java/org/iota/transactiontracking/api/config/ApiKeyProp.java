package org.iota.transactiontracking.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
 */
@Data
@Component
@ConfigurationProperties("api")
public class ApiKeyProp {
    private String apiAccessKey;
}
