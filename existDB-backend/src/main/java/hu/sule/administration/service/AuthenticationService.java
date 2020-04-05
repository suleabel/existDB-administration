package hu.sule.administration.service;

import hu.sule.administration.security.messages.request.LoginForm;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    ResponseEntity<?> signInUser(LoginForm loginRequest);
}
