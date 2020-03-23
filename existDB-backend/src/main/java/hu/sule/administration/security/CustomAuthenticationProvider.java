package hu.sule.administration.security;

import hu.sule.administration.service.UserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserManagerService UserManagerService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try{
            boolean isAdminAccess = UserManagerService.isAdmin();
            if(isAdminAccess){
                return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials().toString());
            } else {
                throw new
                        BadCredentialsException("Authentication failed!!");
            }
        }catch (Exception e){
            throw new BadCredentialsException("Authentication failed!!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class
                .isAssignableFrom(authentication));
    }
}
