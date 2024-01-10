package com.saron.spring.test.product.service;

import com.saron.spring.test.product.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductESServiceImpl implements ProductESService {

    @Override
    @SneakyThrows
    public ProductDto get(String name, String ean) {
        ProductDto dto = new ProductDto();
        dto.setName(name);
        dto.setEan(ean);
        dto.setPrice(100);
        dto.setQuantity(200);
        Thread.sleep(5000);
        return dto;
    }

}
