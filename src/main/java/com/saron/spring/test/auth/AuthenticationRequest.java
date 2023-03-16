package com.saron.spring.test.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {

    private String userEmail;
    private String password;

}
