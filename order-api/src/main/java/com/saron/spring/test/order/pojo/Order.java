package com.saron.spring.test.order.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Order implements Serializable {

    private String city;
    private String county;
    private int itemsPrice;
    private int deliveryPrice;
    private String externalId;
    private List<OrderItem> items = new ArrayList<>();

}
