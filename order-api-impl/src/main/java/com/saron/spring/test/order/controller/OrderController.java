package com.saron.spring.test.order.controller;

import com.saron.spring.test.order.dto.OrderDto;
import com.saron.spring.test.order.endpoint.OrderEndpoint;
import com.saron.spring.test.order.pojo.Order;
import com.saron.spring.test.order.service.OrderSerializationService;
import com.saron.spring.test.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderEndpoint {

    private final OrderService orderService;
    private final OrderSerializationService orderSerializationService;

    @Override
    public String create(OrderDto orderDto) {
        return orderService.create(orderDto);
    }

    @Override
    public void findAll() {
        orderService.findAll();
    }

    @Override
    public void updateItemsPrice(String externalId, int price) {
        orderService.updateItemsPrice(externalId, price);
    }

    @Override
    public void updateDeliveryPrice(String externalId, int price) {
        orderService.updateDeliveryPrice(externalId, price);
    }

    @Override
    public void delete(String externalId) {
        orderService.delete(externalId);
    }

    @Override
    public Order getDeserializedOrder(String externalId) {
        return orderSerializationService.get(externalId);
    }

}
