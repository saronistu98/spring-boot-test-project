package com.saron.spring.test.order.endpoint;

import com.saron.spring.test.order.dto.OrderDto;
import com.saron.spring.test.order.dto.OrderSearchSort;
import com.saron.spring.test.order.dto.PlacedOrderDto;
import com.saron.spring.test.order.pojo.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/order")
public interface OrderEndpoint {

    @PostMapping(path = "/create")
    String create(@RequestBody @Valid OrderDto orderDto);

    @GetMapping(path = "/list")
    Page<PlacedOrderDto> findAll(@RequestBody OrderSearchSort sort, Pageable pageable);

    @PatchMapping(path = "/update-items-price/{externalId}/{price}")
    void updateItemsPrice(@PathVariable String externalId, @PathVariable int price);

    @PatchMapping(path = "/update-delivery-price/{externalId}/{price}")
    void updateDeliveryPrice(@PathVariable String externalId, @PathVariable int price);

    @DeleteMapping(path = "/{externalId}")
    void delete(@PathVariable String externalId);

    @GetMapping(path = "/deserialize/{externalId}")
    Order getDeserializedOrder(@PathVariable String externalId);

}
