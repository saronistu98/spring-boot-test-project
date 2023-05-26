package com.saron.spring.test.messaging;

import com.saron.spring.test.order.dto.PurchasedProductSubtractDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQOrderProducer {

    private final RabbitTemplate rabbitTemplate;

    public void send(PurchasedProductSubtractDto purchasedProductSubtractDto) {
        rabbitTemplate.convertAndSend("OrderExchange", "order.product.purchase", purchasedProductSubtractDto);
    }

}
