package com.example.demo.service;

import com.example.demo.security.CustomAuthenticationProvider;
import com.example.demo.security.messages.request.LoginForm;
import com.example.demo.security.messages.response.JwtResponse;
import com.example.demo.security.jwt.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {

    private CustomAuthenticationProvider authenticationManager;

    private JwtProvider jwtProvider;

    private ExistDbCredentialsService existDbCredentialsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    public void setAuthenticationManager(CustomAuthenticationProvider authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Autowired
    public void setExistDbCredentialsService(ExistDbCredentialsService existDbCredentialsService){
        this.existDbCredentialsService = existDbCredentialsService;
    }

    public ResponseEntity<?> signInUser(LoginForm loginRequest) {
        logger.info("Login request: " + loginRequest.toString());
        String InitDatabaseResponse = existDbCredentialsService.initDatabaseDriver(loginRequest.getUsername(), loginRequest.getPassword(), loginRequest.getUrl());
        if(!InitDatabaseResponse.equals("OK")){
            return new ResponseEntity<>(InitDatabaseResponse, HttpStatus.FORBIDDEN);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt, (String) authentication.getPrincipal()));
    }


}
