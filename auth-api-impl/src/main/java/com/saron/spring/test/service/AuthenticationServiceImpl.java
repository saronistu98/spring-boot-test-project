package com.saron.spring.test.service;

import com.saron.spring.test.AuthenticationRequest;
import com.saron.spring.test.AuthenticationResponse;
import com.saron.spring.test.RegisterRequest;
import com.saron.spring.test.user.dao.UserEntity;
import com.saron.spring.test.user.exception.UserAlreadyExistsException;
import com.saron.spring.test.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if (userService.exists(request.getUserEmail()))
            throw new UserAlreadyExistsException();
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);
        UserEntity userEntity = UserEntity.create(request);
        userService.save(userEntity);
        String jwtToken = jwtService.generateToken(userEntity);
        return new AuthenticationResponse(jwtToken);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(request.getUserEmail(), request.getPassword());
        authenticationManager.authenticate(authentication);
        UserEntity userEntity = userService.findByEmail(request.getUserEmail());
        String jwtToken = jwtService.generateToken(userEntity);
        return new AuthenticationResponse(jwtToken);
    }

}
