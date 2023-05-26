package com.saron.spring.test.config.rabbitMQ_exchanges.properties;

import com.saron.spring.test.config.rabbitMQ_exchanges.RabbitMQPropertiesBase;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rabbitmq.order-properties")
public class RabbitMQOrderProperties extends RabbitMQPropertiesBase {
}
