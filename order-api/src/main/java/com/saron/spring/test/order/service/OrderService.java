package com.saron.spring.test.order.service;

import com.saron.spring.test.order.dto.OrderDto;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    String create(OrderDto orderDto);

    void updateItemsPrice(String orderId, int price);

    void updateDeliveryPrice(String orderId, int price);

    void delete(String orderId);

}
