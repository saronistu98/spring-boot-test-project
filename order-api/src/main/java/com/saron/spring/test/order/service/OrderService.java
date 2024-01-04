package com.saron.spring.test.order.service;

import com.saron.spring.test.order.dto.OrderDto;

public interface OrderService {

    String create(OrderDto orderDto);

    void findAll();

    void updateItemsPrice(String orderId, int price);

    void updateDeliveryPrice(String orderId, int price);

    void delete(String orderId);

}
