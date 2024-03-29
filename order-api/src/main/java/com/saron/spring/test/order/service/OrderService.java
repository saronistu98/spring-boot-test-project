package com.saron.spring.test.order.service;

import com.saron.spring.test.order.dto.OrderDto;
import com.saron.spring.test.order.dto.OrderSearchSort;
import com.saron.spring.test.order.dto.PlacedOrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface OrderService {

    String create(OrderDto orderDto);

    Page<PlacedOrderDto> findAll(OrderSearchSort sort, Pageable pageable);

    void updateItemsPrice(String externalId, int price);

    void updateDeliveryPrice(String externalId, int price);

    void delete(String externalId);

    @Async
    CompletableFuture<Map<String, String>> getMap();

    @Async
    CompletableFuture<List<String>> getList();

}
