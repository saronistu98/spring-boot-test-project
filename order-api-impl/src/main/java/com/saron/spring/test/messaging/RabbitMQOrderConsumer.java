package com.saron.spring.test.messaging;

import com.saron.spring.test.order.dto.PurchasedProductSubtractDto;
import com.saron.spring.test.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQOrderConsumer {

    private final ProductService productService;

    @RabbitListener(queues = "PurchasedProductQueue")
    public void consumeCarouselItemDetailsRequest(PurchasedProductSubtractDto dto) {
        productService.subtractPurchasedQuantity(dto);
    }

    @RabbitListener(queues = "PurchasedProductQueueDeadLetter")
    public void consumeRejectedCarouselItemDetailsRequest(PurchasedProductSubtractDto dto) {
        log.warn("An error occurred: {}", dto);
    }

}
