package com.saron.spring.test.product.service;

import com.saron.spring.test.order.dto.PurchasedProductSubtractDto;
import com.saron.spring.test.product.dto.CachedProductDto;
import com.saron.spring.test.product.dto.ProductDto;
import com.saron.spring.test.product.dto.ProductUpdateDto;

import java.util.List;

public interface ProductService {

    void updateStock(List<ProductUpdateDto> updates);

    ProductDto purchase(Long productId, int quantity);

    Long create(ProductDto productDto);

    List<ProductDto> getAll(String name);

    void delete(String ean);

    void subtractPurchasedQuantity(PurchasedProductSubtractDto dto);

    CachedProductDto get(String name, String ean);

}
