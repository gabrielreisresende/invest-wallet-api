package com.resendegabriel.investwalletapi.service.auth;

import com.resendegabriel.investwalletapi.domain.auth.User;
import com.resendegabriel.investwalletapi.domain.auth.enums.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private TokenService tokenService;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    private static User user;

    @BeforeAll
    static void init() {
        user = new User(1L, "email@email.com", new BCryptPasswordEncoder().encode("12345"), UserRole.CLIENT, Boolean.FALSE);
    }

    @Test
    void shouldGetBearerTokenFromHeaderWhenBearerTokenExists() {
        when(request.getHeader("Authorization")).thenReturn("Bearer mockTokenJWT");

        String response = authenticationService.getBearerTokenFromHeader(request);

        assertEquals("mockTokenJWT", response);
    }

    @Test
    void shouldReturnNullWhenBearerTokenNotExists() {
        when(request.getHeader("Authorization")).thenReturn("Basic token");

        String response = authenticationService.getBearerTokenFromHeader(request);

        assertEquals(null, response);
    }

    @Test
    void shouldGetBasicAuthCredentialsWhenBasicAuthExists() {
        when(request.getHeader("Authorization")).thenReturn("Basic ZW1haWxAZW1haWwuY29tOjEyMzQ1");

        String[] response = authenticationService.getBasicAuthCredentials(request);

        assertArrayEquals(new String[]{"email@email.com", "12345"}, response);
    }

    @Test
    void shouldReturnNullWhenBasicAuthNotExists() {
        when(request.getHeader("Authorization")).thenReturn("Bearer Token");

        String[] response = authenticationService.getBasicAuthCredentials(request);

        assertArrayEquals(null, response);
    }

    @Test
    void shouldAuthenticateWithTokenJWT() {
        when(tokenService.validateToken("mockToken")).thenReturn(user.getEmail());
        when(userService.loadUserByUsername(user.getEmail())).thenReturn(user);

        authenticationService.authenticateWithJWT("mockToken");

        then(tokenService).should().validateToken("mockToken");
        then(userService).should().loadUserByUsername(user.getEmail());
    }


    @Test
    void shouldAuthenticateWithBasicAuth() {
        String[] mockCredentials = {user.getEmail(), "12345"};
        when(userService.loadUserByUsername(user.getEmail())).thenReturn(user);

        var result = authenticationService.authenticateWithBasicAuth(mockCredentials);

        assertEquals(user.getEmail(), result.getName());
        assertTrue(result.isAuthenticated());
        assertEquals(user.getAuthorities(), result.getAuthorities());
    }

    @Test
    void shouldNotAuthenticateWithInvalidCredentials() {
        String[] mockCredentials = {user.getEmail(), "Wrong Password"};
        when(userService.loadUserByUsername(user.getEmail())).thenReturn(user);

        assertThrows(BadCredentialsException.class, () -> authenticationService.authenticateWithBasicAuth(mockCredentials));
    }
}