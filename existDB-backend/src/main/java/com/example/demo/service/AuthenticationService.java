package com.example.demo.service;

import com.example.demo.model.User;

import com.example.demo.security.messages.request.LoginForm;
import com.example.demo.security.messages.request.SignUpForm;
import com.example.demo.security.messages.response.JwtResponse;
import com.example.demo.security.messages.response.ResponseMessage;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtProvider;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {

    private UserRepository userRepository;

    private RoleConverter roleConverter;

    private PasswordEncoder encoder;

    private AuthenticationManager authenticationManager;

    private JwtProvider jwtProvider;

    private ExistDbMainService existDbMainService;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleConverter(RoleConverter roleConverter) {
        this.roleConverter = roleConverter;
    }

    @Autowired
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
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

    public ResponseEntity<?> signUpUser(SignUpForm signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Email is already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        User user = new User(signUpRequest.getUsername(), signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));


        Set<String> strRoles = signUpRequest.getRole();

        user.setRoles(roleConverter.convertRoles(strRoles));

        userRepository.save(user);
        return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
    }

    public ResponseEntity<?> signInUser(LoginForm loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        if(!existDbMainService.isAdmin(loginRequest.getUsername(), loginRequest.getPassword(),loginRequest.getUrl())){
            return new ResponseEntity<>(new ResponseMessage("ExistDb ERROR"),
                    HttpStatus.BAD_REQUEST);
        }

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }


}
