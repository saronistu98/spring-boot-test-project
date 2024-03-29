package com.saron.spring.test.product.endpoint;

import com.saron.spring.test.product.dto.CachedProductDto;
import com.saron.spring.test.product.dto.ProductDto;
import com.saron.spring.test.product.dto.ProductUpdateDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/product")
public interface ProductEndpoint {

    @GetMapping("/{name}/{ean}")
    CachedProductDto get(@PathVariable String name, @PathVariable String ean);

    @GetMapping("/{name}")
    List<ProductDto> getAll(@PathVariable String name);

    @PostMapping(path = "/create")
    Long create(@RequestBody @Valid ProductDto productDto);

    @PatchMapping(path = "/update-stock")
    void updateItemsPrice(@RequestBody List<ProductUpdateDto> updates);

    @PatchMapping(path = "/purchase/{productId}/{quantity}")
    ProductDto updateDeliveryPrice(@PathVariable Long productId, @PathVariable int quantity);

    @DeleteMapping(path = "/{ean}")
    void delete(@PathVariable String ean);

}
