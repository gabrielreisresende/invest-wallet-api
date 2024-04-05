package com.resendegabriel.investwalletapi.service.auth;

import com.resendegabriel.investwalletapi.domain.auth.User;
import com.resendegabriel.investwalletapi.domain.auth.enums.UserRole;
import com.resendegabriel.investwalletapi.exceptions.ResourceNotFoundException;
import com.resendegabriel.investwalletapi.repository.auth.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.plugins.MockMaker;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationService authenticationService;

    private static User user;

    @BeforeAll
    static void init() {
        user = User.builder()
                .userId(1L)
                .email("teste@email.com")
                .password(new BCryptPasswordEncoder().encode("password"))
                .role(UserRole.CUSTOMER)
                .build();
    }

    @Test
    void shouldLoadAUserDetailsByUserEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(user);

        var response = userService.loadUserByUsername(anyString());

        assertEquals(user, response);
        then(userRepository).should().findByEmail(anyString());
        then(userRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldSaveANewUser() {
        var base64Credentials = "base64Credentials";

        when(authenticationService.getBase64Credentials(anyString())).thenReturn(new String[]{user.getEmail(), "password"});
        when(userRepository.save(any(User.class))).thenReturn(user);

        var response = userService.save(base64Credentials);

        assertEquals(user, response);
        then(userRepository).should().save(any(User.class));
        then(userRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldUpdateAUserEmail() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        userService.updateEmail(1L, "novoEmail@email.com");

        assertEquals("novoEmail@email.com", user.getEmail());
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenThereIsNoUserWithThisId() {
        assertThrows(ResourceNotFoundException.class, () -> userService.updateEmail(1L, "novoEmail@email.com"));
    }

    @Test
    void shouldUpdateAnUserPassword(){
        var mockUser = mock(User.class);
        userService.updatePassword("newPassword", mockUser);

        verify(mockUser, times(1)).setPassword(anyString());
    }

    @Test
    void shouldGetAnUserByEmail(){
        when(userRepository.findByEmail(anyString())).thenReturn(user);

        var response = userService.getUserByEmail(user.getEmail());

        assertEquals(response, user);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenTryToGetAnUserWithAnEmailThatDoesNotExist(){
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserByEmail(anyString()));
    }
}