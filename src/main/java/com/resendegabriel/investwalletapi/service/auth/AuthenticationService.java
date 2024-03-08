package com.resendegabriel.investwalletapi.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    public String getBearerTokenFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    public String[] getBasicAuthCredentials(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
            String base64Credentials = authorizationHeader.replace("Basic ", "");
            return getBase64Credentials(base64Credentials);
        }
        return null;
    }

    public String[] getBase64Credentials(String base64Credentials) {
        byte[] decodedBytes = java.util.Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(decodedBytes);
        return credentials.split(":");
    }

    public void authenticateWithJWT(String token) {
        String username = tokenService.validateToken(token);
        var user = userService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public Authentication authenticateWithBasicAuth(String[] credentials) {
        String email = credentials[0];
        String password = credentials[1];

        var user = userService.loadUserByUsername(email);

        if (user == null || !new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
