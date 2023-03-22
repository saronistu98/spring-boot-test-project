package com.saron.spring.test.product.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long productId) {
        super("Product with ID " + productId + " was not found!");
    }

    public ProductNotFoundException(String ean) {
        super("Product with ean " + ean + " was not found!");
    }

}
