package com.saron.spring.test.order.endpoint;

import com.saron.spring.test.order.dto.OrderDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/order")
public interface OrderEndpoint {

    @PostMapping(path = "/create")
    String create(@RequestBody @Valid OrderDto orderDto);

    @PatchMapping(path = "/update-items-price/{orderId}/{price}")
    void updateItemsPrice(@PathVariable String orderId, @PathVariable int price);

    @PatchMapping(path = "/update-delivery-price/{orderId}/{price}")
    void updateDeliveryPrice(@PathVariable String orderId, @PathVariable int price);

    @DeleteMapping(path = "/{orderId}")
    void delete(@PathVariable String orderId);

}
