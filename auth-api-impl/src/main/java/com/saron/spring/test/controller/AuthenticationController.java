package com.saron.spring.test.controller;

import com.saron.spring.test.AuthenticationResponse;
import com.saron.spring.test.RegisterRequest;
import com.saron.spring.test.AuthenticationRequest;
import com.saron.spring.test.endpoint.AuthenticationEndpoint;
import com.saron.spring.test.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationEndpoint {

    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticate = authenticationService.authenticate(request);
        return ResponseEntity.ok(authenticate);
    }

}
