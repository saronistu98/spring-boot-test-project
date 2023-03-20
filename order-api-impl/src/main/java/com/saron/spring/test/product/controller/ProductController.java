package com.saron.spring.test.product.controller;

import com.saron.spring.test.product.dto.ProductDto;
import com.saron.spring.test.product.dto.ProductUpdateDto;
import com.saron.spring.test.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{name}")
    public List<ProductDto> getAll(@PathVariable String name) {
        return productService.getAll(name);
    }

    @PostMapping(path = "/create")
    public Long create(@RequestBody ProductDto productDto) {
        return productService.create(productDto);
    }

    @PatchMapping(path = "/update-stock")
    public void updateItemsPrice(@RequestBody List<ProductUpdateDto> updates) {
        productService.updateStock(updates);
    }

    @PatchMapping(path = "/purchase/{productId}/{quantity}")
    public ProductDto updateDeliveryPrice(@PathVariable Long productId, @PathVariable int quantity) {
        return productService.purchase(productId, quantity);
    }

}
