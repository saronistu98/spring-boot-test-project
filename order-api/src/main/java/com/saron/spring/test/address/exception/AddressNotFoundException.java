package com.saron.spring.test.address.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value = NOT_FOUND)
public class AddressNotFoundException extends RuntimeException {

    public AddressNotFoundException(long id) {
        super("Address not found for ID: " + id);
    }

}
