package com.saron.spring.test.product.dao;

import com.saron.spring.test.base.BaseEntity;
import com.saron.spring.test.product.dto.CachedProductUpdateDto;
import com.saron.spring.test.product.dto.ProductDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "products_cache", indexes = @Index(columnList = "name, ean", unique = true))
public class CachedProductEntity extends BaseEntity {

    private String name;
    private String ean;
    private int price;
    private int quantity;
    private boolean isLoadedIntoCache = true;

    public static CachedProductEntity create(ProductDto productDto) {
        CachedProductEntity entity = new CachedProductEntity();
        entity.name = productDto.getName();
        entity.ean = productDto.getEan();
        entity.price = productDto.getPrice();
        entity.quantity = productDto.getQuantity();
        return entity;
    }

    public void update(CachedProductUpdateDto dto) {
        this.ean = dto.getEan();
        this.name = dto.getName();
        this.quantity = dto.getQuantity();
        this.price = dto.getPrice();
    }

}
