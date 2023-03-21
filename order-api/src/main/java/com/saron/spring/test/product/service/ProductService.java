package com.saron.spring.test.product.service;

import com.saron.spring.test.product.dto.ProductDto;
import com.saron.spring.test.product.dto.ProductUpdateDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    void updateStock(List<ProductUpdateDto> updates);

    ProductDto purchase(Long productId, int quantity);

    Long create(ProductDto productDto);

    List<ProductDto> getAll(String name);

    void delete(String ean);

}
