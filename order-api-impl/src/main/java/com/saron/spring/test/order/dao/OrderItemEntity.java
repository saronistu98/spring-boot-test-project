package com.saron.spring.test.order.dao;

import com.saron.spring.test.base.BaseEntity;
import com.saron.spring.test.order.dto.OrderItemDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Table(name = "order_items")
public class OrderItemEntity extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    private OrderEntity order;
    private int quantity;
    private int price;
    private String ean;

    public static OrderItemEntity create(OrderItemDto orderItemDto, OrderEntity orderEntity) {
        OrderItemEntity entity = new OrderItemEntity();
        entity.price = orderItemDto.getPrice();
        entity.quantity = orderItemDto.getQuantity();
        entity.ean = orderItemDto.getEan();
        entity.order = orderEntity;
        return entity;
    }

}
