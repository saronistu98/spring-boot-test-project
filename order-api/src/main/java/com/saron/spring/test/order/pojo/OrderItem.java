package com.saron.spring.test.order.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OrderItem implements Serializable {

    private int quantity;
    private int price;
    private String ean;

}
