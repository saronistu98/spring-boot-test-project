package com.saron.spring.test.order.service;

import com.saron.spring.test.mapper.OrderMapper;
import com.saron.spring.test.messaging.RabbitMQOrderProducer;
import com.saron.spring.test.order.dao.OrderEntity;
import com.saron.spring.test.order.dao.OrderItemEntity;
import com.saron.spring.test.order.dao.OrderRepository;
import com.saron.spring.test.order.dto.OrderDto;
import com.saron.spring.test.order.dto.OrderSearchSort;
import com.saron.spring.test.order.dto.PlacedOrderDto;
import com.saron.spring.test.order.exception.OrderNotFoundException;
import com.saron.spring.test.order.pojo.Order;
import com.saron.spring.test.order.specification.OrderSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
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
    public Page<PlacedOrderDto> findAll(OrderSearchSort sort, Pageable pageable) {
        OrderSpecification specification = new OrderSpecification(sort);
        return orderRepository.findAll(specification, pageable)
                .map(orderMapper::toPlacedOrderDto);
    }

    @Override
    @Retryable(value = ObjectOptimisticLockingFailureException.class, maxAttempts = 4, backoff = @Backoff(delay = 1000, multiplier = 2))
    public void updateItemsPrice(String externalId, int price) {
        int retryCount = RetrySynchronizationManager.getContext().getRetryCount();
        if (retryCount != 0)
            log.info("updateItemsPrice() method retry count: {}", retryCount);
        log.info("Fetching order entity at {}", LocalDateTime.now());
        OrderEntity orderEntity = getOrderEntity(externalId);
        log.info("Fetched order entity at {}", LocalDateTime.now());
        orderEntity.setItemsPrice(price);
        log.info("Saving order entity at {}", LocalDateTime.now());
        orderRepository.save(orderEntity);
        log.info("Saved order entity at {}", LocalDateTime.now());
    }

    @Recover
    @SuppressWarnings("unused")
    public void recover(ObjectOptimisticLockingFailureException ex, String externalId, int price) {
        log.warn("Optimistic lock retry failed after multiple attempts or updating items price for order with external ID: {}", externalId);
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
