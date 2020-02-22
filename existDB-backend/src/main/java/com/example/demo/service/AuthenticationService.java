package com.example.demo.service;

import com.example.demo.security.CustomAuthenticationProvider;
import com.example.demo.security.messages.request.LoginForm;
import com.example.demo.security.messages.response.JwtResponse;
import com.example.demo.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {

    private CustomAuthenticationProvider authenticationManager;

    private JwtProvider jwtProvider;

    private ExistDbMainService existDbMainService;

    @Autowired
    public void setAuthenticationManager(CustomAuthenticationProvider authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Autowired
    public void setExistDbMainService(ExistDbMainService existDbMainService){
        this.existDbMainService = existDbMainService;
    }

    public ResponseEntity<?> signInUser(LoginForm loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        existDbMainService.initDatabaseDriver(loginRequest.getUsername(), loginRequest.getPassword(), loginRequest.getUrl());

        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt, (String) authentication.getPrincipal()));
    }


}
