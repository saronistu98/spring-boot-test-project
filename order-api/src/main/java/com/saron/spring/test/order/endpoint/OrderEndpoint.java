package com.saron.spring.test.order.endpoint;

import com.saron.spring.test.order.dto.OrderDto;
import com.saron.spring.test.order.dto.PlacedOrderDto;
import com.saron.spring.test.order.pojo.Order;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/order")
public interface OrderEndpoint {

    @PostMapping(path = "/create")
    String create(@RequestBody @Valid OrderDto orderDto);

    @GetMapping(path = "/list")
    List<PlacedOrderDto> findAll();

    @PatchMapping(path = "/update-items-price/{externalId}/{price}")
    void updateItemsPrice(@PathVariable String externalId, @PathVariable int price);

    @PatchMapping(path = "/update-delivery-price/{externalId}/{price}")
    void updateDeliveryPrice(@PathVariable String externalId, @PathVariable int price);

    @DeleteMapping(path = "/{externalId}")
    void delete(@PathVariable String externalId);

    @GetMapping(path = "/deserialize/{externalId}")
    Order getDeserializedOrder(@PathVariable String externalId);

}
