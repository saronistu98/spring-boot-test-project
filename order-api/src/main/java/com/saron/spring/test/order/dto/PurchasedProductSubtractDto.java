package com.saron.spring.test.order.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PurchasedProductSubtractDto {

    private String ean;
    private int quantity;

}
