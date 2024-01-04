package com.saron.spring.test.service;

import com.saron.spring.test.AuthenticationRequest;
import com.saron.spring.test.AuthenticationResponse;
import com.saron.spring.test.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

}
