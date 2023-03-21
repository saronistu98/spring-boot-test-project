package com.saron.spring.test.order.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderDto {

    @NotNull
    private String city;
    @NotNull
    private String county;
    @Min(1)
    private int itemsPrice;
    private int deliveryPrice;
    private List<OrderItemDto> items = new ArrayList<>();

}
