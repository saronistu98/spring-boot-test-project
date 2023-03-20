package com.saron.spring.test.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {

    private String city;
    private String county;
    private int itemsPrice;
    private int deliveryPrice;

}
