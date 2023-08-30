package com.saron.spring.test.order.dao;

import com.saron.spring.test.base.BaseEntity;
import com.saron.spring.test.order.dto.OrderItemDto;
import com.saron.spring.test.order.dto.PurchasedProductSubtractDto;
import com.saron.spring.test.order.enums.ItemType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static com.saron.spring.test.order.enums.ItemType.PRODUCT;
import static javax.persistence.EnumType.STRING;
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
    @Enumerated(STRING)
    private ItemType type = PRODUCT;

    public static OrderItemEntity create(OrderItemDto orderItemDto, OrderEntity orderEntity) {
        OrderItemEntity entity = new OrderItemEntity();
        entity.price = orderItemDto.getPrice();
        entity.quantity = orderItemDto.getQuantity();
        entity.ean = orderItemDto.getEan();
        entity.order = orderEntity;
        return entity;
    }

    public PurchasedProductSubtractDto toPurchasedProductSubtractDto() {
        PurchasedProductSubtractDto dto = new PurchasedProductSubtractDto();
        dto.setEan(ean);
        dto.setQuantity(quantity);
        return dto;
    }

}
