package com.saron.spring.test.product.controller;

import com.saron.spring.test.product.dto.ProductDto;
import com.saron.spring.test.product.dto.ProductUpdateDto;
import com.saron.spring.test.product.endpoint.ProductEndpoint;
import com.saron.spring.test.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductEndpoint {

    private final ProductService productService;

    @Override
    public List<ProductDto> getAll(String name) {
        return productService.getAll(name);
    }

    @Override
    public Long create(ProductDto productDto) {
        return productService.create(productDto);
    }

    @Override
    public void updateItemsPrice(List<ProductUpdateDto> updates) {
        productService.updateStock(updates);
    }

    @Override
    public ProductDto updateDeliveryPrice(Long productId, int quantity) {
        return productService.purchase(productId, quantity);
    }

}
