package com.saron.spring.test;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {

    private String userEmail;
    private String password;

}
