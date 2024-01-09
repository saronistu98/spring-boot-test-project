package com.saron.spring.test.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderAsyncServiceImpl implements OrderAsyncService {

    private final OrderService orderService;

    @Override
    @PostConstruct
    public void perform() {
        CompletableFuture<Map<String, String>> mapCompletableFuture = orderService.getMap();
        CompletableFuture<List<String>> listCompletableFuture = orderService.getList();
        CompletableFuture.allOf(mapCompletableFuture, listCompletableFuture).join();
        try {
            Map<String, String> map = mapCompletableFuture.get();
            List<String> list = listCompletableFuture.get();
            log.info(map.get(list.get(0)));
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
        }
    }

}
