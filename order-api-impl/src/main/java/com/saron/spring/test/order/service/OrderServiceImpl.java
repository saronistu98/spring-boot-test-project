package com.saron.spring.test.order.service;

import com.saron.spring.test.order.dao.OrderEntity;
import com.saron.spring.test.order.dao.OrderItemEntity;
import com.saron.spring.test.order.dao.OrderRepository;
import com.saron.spring.test.order.dto.OrderDto;
import com.saron.spring.test.order.dto.PurchasedProductSubtractDto;
import com.saron.spring.test.order.dto.PurchasedProductSubtractResponseDto;
import com.saron.spring.test.order.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public String create(OrderDto orderDto) {
        long numberOfOrders = orderRepository.count();
        String orderId = OrderIdEncoder.encode(numberOfOrders);
        OrderEntity orderEntity = OrderEntity.create(orderId, orderDto);
        orderEntity.getItems().stream()
                .map(OrderItemEntity::toPurchasedProductSubtractDto)
                .forEach(this::sendWithRabbit);
        orderRepository.save(orderEntity);
        return orderEntity.getOrderId();
    }

    public void sendWithRabbit(PurchasedProductSubtractDto dto) {
        PurchasedProductSubtractResponseDto send = send(dto);
        System.out.println(send.getName());
        System.out.println(send.getQuantityLeft());
    }

    private PurchasedProductSubtractResponseDto send(PurchasedProductSubtractDto dto) {
        return rabbitTemplate.convertSendAndReceiveAsType("OrderExchange", "order.product.purchase", dto, new ParameterizedTypeReference<>() {
        });
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

    @Override
    @Transactional
    public void delete(String orderId) {
        orderRepository.deleteByOrderId(orderId);
    }

    private OrderEntity getOrderEntity(String orderId) {
        return orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

}
