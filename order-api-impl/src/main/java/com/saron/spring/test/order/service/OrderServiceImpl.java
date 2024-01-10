package com.saron.spring.test.order.service;

import com.saron.spring.test.mapper.OrderMapper;
import com.saron.spring.test.messaging.RabbitMQOrderProducer;
import com.saron.spring.test.order.dao.OrderEntity;
import com.saron.spring.test.order.dao.OrderItemEntity;
import com.saron.spring.test.order.dao.OrderRepository;
import com.saron.spring.test.order.dto.OrderDto;
import com.saron.spring.test.order.dto.PlacedOrderDto;
import com.saron.spring.test.order.exception.OrderNotFoundException;
import com.saron.spring.test.order.pojo.Order;
import com.saron.spring.test.order.specification.OrderSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final RabbitMQOrderProducer rabbitMQOrderProducer;
    private final OrderSerializationService orderSerializationService;

    @Override
    public String create(OrderDto orderDto) {
        long numberOfOrders = orderRepository.count();
        String orderExternalId = OrderIdEncoder.encode(numberOfOrders);
        Order order = orderMapper.dtoToPojo(orderDto);
        order.setExternalId(orderExternalId);
        OrderEntity orderEntity = OrderEntity.create(order);
        orderEntity.getItems().stream()
                .map(OrderItemEntity::toPurchasedProductSubtractDto)
                .forEach(rabbitMQOrderProducer::send);
        orderRepository.save(orderEntity);
        orderSerializationService.save(order);
        return orderEntity.getExternalId();
    }

    @Override
    public List<PlacedOrderDto> findAll() {
        OrderSpecification specification = new OrderSpecification();
        return orderRepository.findAll(specification).stream()
                .map(orderMapper::toPlacedOrderDto)
                .toList();
    }

    @Override
    public void updateItemsPrice(String externalId, int price) {
        OrderEntity orderEntity = getOrderEntity(externalId);
        orderEntity.setItemsPrice(price);
        orderRepository.save(orderEntity);
    }

    @Override
    public void updateDeliveryPrice(String externalId, int price) {
        OrderEntity orderEntity = getOrderEntity(externalId);
        orderEntity.setDeliveryPrice(price);
        orderRepository.save(orderEntity);
    }

    @Override
    @Transactional
    public void delete(String externalId) {
        orderRepository.deleteByExternalId(externalId);
    }

    @Override
    public CompletableFuture<Map<String, String>> getMap() {
        return CompletableFuture.completedFuture(Map.of("key", "value"));
    }

    @Override
    public CompletableFuture<List<String>> getList() {
        return CompletableFuture.completedFuture(List.of("key"));
    }

    private OrderEntity getOrderEntity(String externalId) {
        return orderRepository.findByExternalId(externalId)
                .orElseThrow(() -> new OrderNotFoundException(externalId));
    }

}
