package com.saron.spring.test.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.saron.spring.test.order.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createDate;
    private List<OrderItemDto> items = new ArrayList<>();

}
