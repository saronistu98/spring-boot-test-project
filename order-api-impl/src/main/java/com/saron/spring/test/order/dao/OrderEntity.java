package com.saron.spring.test.order.dao;

import com.saron.spring.test.base.BaseEntity;
import com.saron.spring.test.order.dto.OrderDto;
import com.saron.spring.test.order.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.saron.spring.test.order.enums.OrderStatus.NEW;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

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
    @Enumerated(STRING)
    private OrderStatus status = NEW;
    @OneToMany(mappedBy = "order", cascade = ALL, fetch = LAZY, orphanRemoval = true)
    private List<OrderItemEntity> items = new ArrayList<>();

    public static OrderEntity create(String orderId, OrderDto orderDto) {
        OrderEntity entity = new OrderEntity();
        entity.city = orderDto.getCity();
        entity.county = orderDto.getCounty();
        entity.deliveryPrice = orderDto.getDeliveryPrice();
        entity.itemsPrice = orderDto.getItemsPrice();
        entity.orderId = orderId;
        entity.items = orderDto.getItems().stream()
                .map(item -> OrderItemEntity.create(item, entity))
                .collect(Collectors.toList());
        return entity;
    }

}
