package com.saron.spring.test.config.rabbitMQ_exchanges;

import com.saron.spring.test.config.rabbitMQ_exchanges.properties.RabbitMQOrderProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PurchasedProductConfig {

    private final RabbitMQOrderProperties rabbitMQOrderProperties;

    @Bean
    public RabbitAdmin declarePurchasedProductConfig(RabbitAdmin rabbitAdmin) {
        declareSimpleQueueExchangeAndBinding(rabbitAdmin);
        declareDeadLetterQueueExchangeAndBinding(rabbitAdmin);
        return rabbitAdmin;
    }

    private void declareSimpleQueueExchangeAndBinding(RabbitAdmin rabbitAdmin) {
        Queue queue = QueueBuilder.durable(rabbitMQOrderProperties.getQueue())
                .withArgument("x-dead-letter-exchange", rabbitMQOrderProperties.getDeadLetterExchange())
                .withArgument("x-dead-letter-routing-key", rabbitMQOrderProperties.getDeadLetterRoutingKey())
                .build();
        DirectExchange exchange = new DirectExchange(rabbitMQOrderProperties.getExchange());
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(rabbitMQOrderProperties.getRoutingKey()));
    }

    private void declareDeadLetterQueueExchangeAndBinding(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(rabbitMQOrderProperties.getDeadLetterQueue());
        DirectExchange exchange = new DirectExchange(rabbitMQOrderProperties.getDeadLetterExchange());
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(rabbitMQOrderProperties.getRoutingKey()));
    }

}
