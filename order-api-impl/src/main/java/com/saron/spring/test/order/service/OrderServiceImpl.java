package com.saron.spring.test.order.service;

import com.saron.spring.test.order.exception.OrderNotFoundException;
import com.saron.spring.test.order.dao.OrderEntity;
import com.saron.spring.test.order.dao.OrderRepository;
import com.saron.spring.test.order.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public String create(OrderDto orderDto) {
        long numberOfOrders = orderRepository.count();
        String orderId = OrderIdEncoder.encode(numberOfOrders);
        OrderEntity orderEntity = OrderEntity.create(orderId, orderDto);
        orderRepository.save(orderEntity);
        return orderEntity.getOrderId();
    }

    @Override
    public void updateItemsPrice(String orderId, int price) {
        OrderEntity orderEntity = getOrderEntity(orderId);
        orderEntity.setItemsPrice(price);
        orderRepository.save(orderEntity);
    }

    @Override
    public void updateDeliveryPrice(String orderId, int price) {
        OrderEntity orderEntity = getOrderEntity(orderId);
        orderEntity.setDeliveryPrice(price);
        orderRepository.save(orderEntity);
    }

    private OrderEntity getOrderEntity(String orderId) {
        return orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

}
