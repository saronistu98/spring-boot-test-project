package com.saron.spring.test.config.rabbitMQ;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "rabbit-mq.config")
public class RabbitMQProperties {

    private String hostname;
    private int port;
    private String username;
    private String password;
    private String virtualHost;

}
