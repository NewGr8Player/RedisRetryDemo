package com.xavier.retryable.redisretrydemo.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "mail.sender.retry")
public class RetryProperties {
    @Value("${times:15}")
    private Long times;

    @Value("${duration:5000}")
    private Long duration;

}
