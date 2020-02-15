package com.example.demo.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthEntryPoint.class);

    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e)
            throws IOException, ServletException {
        logger.error("Unauthorized error. Message - {}", e.getMessage());

        final String expired = (String) request.getAttribute("expired");
        if (expired != null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, expired);
        } else if (e.getMessage().equals("Bad credentials")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Username or password is incorrect");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Other error");
            logger.error("Other error - {}", e.getMessage());
        }
    }

}
