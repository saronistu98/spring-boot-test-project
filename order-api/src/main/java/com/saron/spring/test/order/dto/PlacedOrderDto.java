package com.saron.spring.test.order.dto;

import com.saron.spring.test.order.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PlacedOrderDto {

    private String city;
    private String county;
    private int itemsPrice;
    private int deliveryPrice;
    private OrderStatus status;
    private LocalDateTime createDate;
    private List<OrderItemDto> items = new ArrayList<>();

}
