package com.resendegabriel.investwalletapi.configuration.security;

import com.resendegabriel.investwalletapi.service.auth.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenJWT = authenticationService.getBearerTokenFromHeader(request);
        if (tokenJWT != null) {
            authenticationService.authenticateWithJWT(tokenJWT);
        } else {
            String[] basicAuthCredentials = authenticationService.getBasicAuthCredentials(request);
            if (basicAuthCredentials != null) {
                authenticationService.authenticateWithBasicAuth(basicAuthCredentials);
            }
        }
        filterChain.doFilter(request, response);
    }
}