package com.saron.spring.test.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchasedProductSubtractResponseDto {

    private String name;
    private int quantityLeft;

}
