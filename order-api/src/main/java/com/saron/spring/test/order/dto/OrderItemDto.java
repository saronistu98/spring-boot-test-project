package com.saron.spring.test.order.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.EAN;

import javax.validation.constraints.Min;

@Getter
@Setter
public class OrderItemDto {

    @Min(1)
    private int quantity;
    @Min(1)
    private int price;
    @EAN
    private String ean;

}
