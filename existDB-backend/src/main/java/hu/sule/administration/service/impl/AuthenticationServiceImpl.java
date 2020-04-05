package hu.sule.administration.service.impl;

import hu.sule.administration.security.CustomAuthenticationProvider;
import hu.sule.administration.security.messages.request.LoginForm;
import hu.sule.administration.security.messages.response.JwtResponse;
import hu.sule.administration.security.jwt.JwtProvider;
import hu.sule.administration.service.AuthenticationService;
import hu.sule.administration.service.ExistDbCredentialsService;
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
public class AuthenticationServiceImpl implements AuthenticationService {
    private CustomAuthenticationProvider authenticationManager;
    private JwtProvider jwtProvider;
    private ExistDbCredentialsService existDbCredentialsServiceImpl;

    @Autowired
    public void setAuthenticationManager(CustomAuthenticationProvider authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }
    @Autowired
    public void setExistDbCredentialsServiceImpl(ExistDbCredentialsService existDbCredentialsServiceImpl) {
        this.existDbCredentialsServiceImpl = existDbCredentialsServiceImpl;
    }

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    public ResponseEntity<?> signInUser(LoginForm loginRequest) {
        logger.info("Login request: " + loginRequest.toString());
        String InitDatabaseResponse = existDbCredentialsServiceImpl.initDatabaseDriver(loginRequest.getUsername(), loginRequest.getPassword(), loginRequest.getUrl());
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
