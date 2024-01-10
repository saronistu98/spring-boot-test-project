package com.saron.spring.test.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CachedProductUpdateDto {

    private String name;
    private String ean;
    private int price;
    private int quantity;

}
