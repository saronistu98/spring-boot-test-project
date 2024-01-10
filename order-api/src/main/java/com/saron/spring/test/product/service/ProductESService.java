package com.saron.spring.test.product.service;

import com.saron.spring.test.product.dto.ProductDto;

public interface ProductESService {

    ProductDto get(String name, String ean);

}
