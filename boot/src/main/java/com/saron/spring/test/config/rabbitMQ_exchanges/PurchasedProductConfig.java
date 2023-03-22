package com.saron.spring.test.config.rabbitMQ_exchanges;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PurchasedProductConfig {

    @Bean
    public RabbitAdmin declarePurchasedProductConfig(RabbitAdmin rabbitAdmin) {
        declareSimpleQueueExchangeAndBinding(rabbitAdmin);
        declareDeadLetterQueueExchangeAndBinding(rabbitAdmin);
        return rabbitAdmin;
    }

    private void declareSimpleQueueExchangeAndBinding(RabbitAdmin rabbitAdmin) {
        Queue queue = QueueBuilder.durable("PurchasedProductQueue")
                .withArgument("x-dead-letter-exchange", "OrderExchangeDL")
                .withArgument("x-dead-letter-routing-key", "order.product.purchase.dl")
                .build();
        DirectExchange exchange = new DirectExchange("OrderExchange");
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("order.product.purchase"));
    }

    private void declareDeadLetterQueueExchangeAndBinding(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue("PurchasedProductQueueDeadLetter");
        DirectExchange exchange = new DirectExchange("OrderExchangeDL");
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("order.product.purchase.dl"));
    }

}
