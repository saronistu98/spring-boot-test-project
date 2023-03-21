package com.saron.spring.test.product.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.EAN;

@Getter
@Setter
public class ProductDto {

    private String name;
    @EAN
    private String ean;
    private int price;
    private int quantity;

}
