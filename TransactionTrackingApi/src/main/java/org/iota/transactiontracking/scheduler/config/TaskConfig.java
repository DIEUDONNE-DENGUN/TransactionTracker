package org.iota.transactiontracking.scheduler.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("scheduler")
public class TaskConfig {
    private Long duration;
    private String apiHost;
    private String apiPath;
    private String apiKey;
}
