package com.saron.spring.test.user.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.ALREADY_REPORTED;

@ResponseStatus(ALREADY_REPORTED)
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("User already exists!");
    }

}
