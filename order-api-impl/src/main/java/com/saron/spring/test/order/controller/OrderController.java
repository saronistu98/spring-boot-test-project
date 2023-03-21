package com.saron.spring.test.order.controller;

import com.saron.spring.test.order.endpoint.OrderEndpoint;
import com.saron.spring.test.order.dto.OrderDto;
import com.saron.spring.test.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderEndpoint {

    private final OrderService orderService;

    @Override
    public String create(OrderDto orderDto) {
        return orderService.create(orderDto);
    }

    @Override
    public void updateItemsPrice(String orderId, int price) {
        orderService.updateItemsPrice(orderId, price);
    }

    @Override
    public void updateDeliveryPrice(String orderId, int price) {
        orderService.updateDeliveryPrice(orderId, price);
    }

}
