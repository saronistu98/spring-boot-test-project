package com.saron.spring.test;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String userEmail;
    private String password;

}
