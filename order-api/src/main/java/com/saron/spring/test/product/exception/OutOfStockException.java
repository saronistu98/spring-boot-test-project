package com.saron.spring.test.product.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;

@ResponseStatus(PRECONDITION_FAILED)
public class OutOfStockException extends RuntimeException {

    public OutOfStockException(Long productId) {
        super("Product with ID \"" + productId + "\" is out of stock!");
    }

}
