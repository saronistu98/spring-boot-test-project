package com.saron.spring.test.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(path = "/create")
    public String create(@RequestBody OrderDto orderDto) {
        return orderService.create(orderDto);
    }

    @PatchMapping(path = "/update-items-price/{orderId}/{price}")
    public void updateItemsPrice(@PathVariable String orderId, @PathVariable int price) {
        orderService.updateItemsPrice(orderId, price);
    }

    @PatchMapping(path = "/update-delivery-price/{orderId}/{price}")
    public void updateDeliveryPrice(@PathVariable String orderId, @PathVariable int price) {
        orderService.updateDeliveryPrice(orderId, price);
    }

}
