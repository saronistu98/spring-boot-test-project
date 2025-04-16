package com.saron.spring.test.order.controller;

import com.saron.spring.test.order.dto.OrderDto;
import com.saron.spring.test.order.dto.OrderSearchSort;
import com.saron.spring.test.order.dto.PlacedOrderDto;
import com.saron.spring.test.order.endpoint.OrderEndpoint;
import com.saron.spring.test.order.pojo.Order;
import com.saron.spring.test.order.service.OrderSerializationService;
import com.saron.spring.test.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
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
    public Page<PlacedOrderDto> findAll(OrderSearchSort sort, Pageable pageable) {
        return orderService.findAll(sort, pageable);
    }

    @Override
    public void updateItemsPrice(String externalId, int price) {
        orderService.updateItemsPrice(externalId, price);
        log.info("Closed the update items price transaction at {}", LocalDateTime.now());
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
