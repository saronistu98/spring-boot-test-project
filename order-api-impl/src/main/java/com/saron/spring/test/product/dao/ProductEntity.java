package com.saron.spring.test.product.dao;

import com.saron.spring.test.base.BaseEntity;
import com.saron.spring.test.product.dto.ProductDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Table
@Entity(name = "products")
public class ProductEntity extends BaseEntity {

    private String name;
    private String ean;
    private int price;
    private int quantity;

    public static ProductEntity create(ProductDto productDto) {
        ProductEntity entity = new ProductEntity();
        entity.name = productDto.getName();
        entity.ean = productDto.getEan();
        entity.price = productDto.getPrice();
        entity.quantity = productDto.getQuantity();
        return entity;
    }

    public ProductDto toDto() {
        ProductDto dto = new ProductDto();
        dto.setName(name);
        dto.setEan(ean);
        dto.setPrice(price);
        dto.setQuantity(quantity);
        return dto;
    }

}
