package com.saron.spring.test.endpoint;

import com.saron.spring.test.AuthenticationResponse;
import com.saron.spring.test.RegisterRequest;
import com.saron.spring.test.AuthenticationRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public interface AuthenticationEndpoint {

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request);

    @PostMapping("/authenticate")
    @Operation(summary = "Authenticate user")
    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request);

}
