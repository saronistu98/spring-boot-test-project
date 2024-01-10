package com.saron.spring.test.product.pojo;

import com.saron.spring.test.product.dto.CachedProductDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPendingForCache {

    private String key;
    private CachedProductDto product;

}
