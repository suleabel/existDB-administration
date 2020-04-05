package hu.sule.administration.controller;

import hu.sule.administration.security.messages.request.LoginForm;
import hu.sule.administration.service.AuthenticationService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private AuthenticationService authenticationServiceImpl;

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationServiceImpl) {
        this.authenticationServiceImpl = authenticationServiceImpl;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        return authenticationServiceImpl.signInUser(loginRequest);
    }

}