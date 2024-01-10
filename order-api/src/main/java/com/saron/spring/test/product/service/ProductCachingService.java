package com.saron.spring.test.product.service;

import com.saron.spring.test.product.dto.CachedProductDto;
import com.saron.spring.test.product.dto.CachedProductUpdateDto;

public interface ProductCachingService {

    void invalidate();

    CachedProductDto get(String name, String ean);

    void update(CachedProductUpdateDto dto);

}
