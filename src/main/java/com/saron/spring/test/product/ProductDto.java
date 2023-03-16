package com.saron.spring.test.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private String name;
    private String ean;
    private int price;
    private int quantity;

}
