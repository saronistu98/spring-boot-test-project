package com.saron.spring.test.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@ToString
@Table(name = "orders")
public class OrderEntity extends BaseEntity {

    private String city;
    private String county;
    private int itemsPrice;
    private int deliveryPrice;
    private String orderId;

    public static OrderEntity create(String orderId, OrderDto orderDto) {
        OrderEntity entity = new OrderEntity();
        entity.city = orderDto.getCity();
        entity.county = orderDto.getCounty();
        entity.deliveryPrice = orderDto.getDeliveryPrice();
        entity.itemsPrice = orderDto.getItemsPrice();
        entity.orderId = orderId;
        return entity;
    }

}
