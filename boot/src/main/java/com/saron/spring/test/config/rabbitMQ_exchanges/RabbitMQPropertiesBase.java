package com.saron.spring.test.config.rabbitMQ_exchanges;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RabbitMQPropertiesBase {

    private String queue;
    private String deadLetterQueue;
    private String exchange;
    private String deadLetterExchange;
    private String routingKey;
    private String deadLetterRoutingKey;

}

