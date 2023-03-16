package com.saron.spring.test.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public String create(OrderDto orderDto) {
        long numberOfOrders = orderRepository.count();
        String orderId = OrderIdEncoder.encode(numberOfOrders);
        OrderEntity orderEntity = OrderEntity.create(orderId, orderDto);
        orderRepository.save(orderEntity);
        return orderEntity.getOrderId();
    }

    public void updateItemsPrice(String orderId, int price) {
        OrderEntity orderEntity = getOrderEntity(orderId);
        orderEntity.setItemsPrice(price);
        orderRepository.save(orderEntity);
    }

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
