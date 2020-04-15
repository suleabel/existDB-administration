package hu.sule.administration.security;

import hu.sule.administration.exceptions.ApiException;
import hu.sule.administration.service.UserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.xmldb.api.base.XMLDBException;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {


    private UserManagerService UserManagerServiceImpl;

    @Autowired
    public void setUserManagerServiceImpl(UserManagerService userManagerServiceImpl) {
        UserManagerServiceImpl = userManagerServiceImpl;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws BadCredentialsException {
        try{
            boolean isAdminAccess = UserManagerServiceImpl.isAdmin();
            if(isAdminAccess){
                return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials().toString());
            } else {
                throw new
                        BadCredentialsException("Authentication failed!!");
            }
        }catch (XMLDBException e){
            throw new ApiException(e.getMessage(),"authenticate","XMLDBException", e.getStackTrace());
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
