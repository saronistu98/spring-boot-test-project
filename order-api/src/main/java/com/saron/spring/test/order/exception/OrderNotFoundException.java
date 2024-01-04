package com.saron.spring.test.order.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value = NOT_FOUND)
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String externalId) {
        super("Order not found for ID: " + externalId);
    }

}
