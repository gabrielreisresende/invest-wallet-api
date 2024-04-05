package com.resendegabriel.investwalletapi.service.auth;

import com.resendegabriel.investwalletapi.domain.auth.PasswordResetCode;
import com.resendegabriel.investwalletapi.domain.auth.User;
import com.resendegabriel.investwalletapi.domain.auth.dto.EmailDTO;
import com.resendegabriel.investwalletapi.domain.auth.dto.ResetPasswordDTO;
import com.resendegabriel.investwalletapi.domain.auth.dto.TwoFactorCodeDTO;
import com.resendegabriel.investwalletapi.domain.auth.enums.UserRole;
import com.resendegabriel.investwalletapi.repository.auth.PasswordResetCodeRepository;
import com.resendegabriel.investwalletapi.service.mail.IMailService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PasswordResetServiceTest {

    @InjectMocks
    private PasswordResetService passwordResetService;

    @Mock
    private PasswordResetCodeRepository passwordResetCodeRepository;

    @Mock
    private UserService userService;

    @Mock
    private IMailService mailService;

    private static PasswordResetCode passwordResetCode;

    private static TwoFactorCodeDTO twoFactorCodeDTO;

    private static EmailDTO emailDTO;

    private static User user;

    private static ResetPasswordDTO resetPasswordDTO;

    @BeforeAll
    static void init() {
        user = User.builder()
                .userId(1L)
                .email("teste@email.com")
                .password(new BCryptPasswordEncoder().encode("password"))
                .role(UserRole.CUSTOMER)
                .build();
        emailDTO = new EmailDTO("teste@email.com");
        passwordResetCode = new PasswordResetCode(1234, user);
        twoFactorCodeDTO = new TwoFactorCodeDTO(1234, "user@email.com");
        var encryptedPassword = Base64.getEncoder().encode("newPassword".getBytes());
        resetPasswordDTO = new ResetPasswordDTO(Arrays.toString(encryptedPassword), "user@gmail.com");
    }

    @Test
    void shouldSendATwoFactorCodeToTheUserMail() {
        when(userService.getUserByEmail(anyString())).thenReturn(user);
        when(passwordResetCodeRepository.findByUser_UserId(anyLong())).thenReturn(null);

        passwordResetService.generatePasswordResetCode(emailDTO);

        then(mailService).should().sendResetPasswordCodeToEmail(anyString(), anyInt());
        then(passwordResetCodeRepository).should().findByUser_UserId(anyLong());
        then(passwordResetCodeRepository).should().save(any(PasswordResetCode.class));
        then(passwordResetCodeRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldResendATwoFactorCodeToTheUserMail() {
        when(userService.getUserByEmail(anyString())).thenReturn(user);
        when(passwordResetCodeRepository.findByUser_UserId(anyLong())).thenReturn(passwordResetCode);

        passwordResetService.generatePasswordResetCode(emailDTO);

        then(mailService).should().sendResetPasswordCodeToEmail(anyString(), anyInt());
        then(passwordResetCodeRepository).should().findByUser_UserId(anyLong());
        then(passwordResetCodeRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldValidateTwoFactorCode_Expected_Exception() {
        when(userService.getUserByEmail(anyString())).thenReturn(user);
        when(passwordResetCodeRepository.existsByCodeAndUser_UserId(anyInt(), anyLong())).thenReturn(false);

        assertThrows(ValidationException.class, () -> passwordResetService.validateTwoFactorCode(twoFactorCodeDTO));
    }

    @Test
    void shouldResetPassword() {
        when(userService.getUserByEmail(anyString())).thenReturn(user);

        passwordResetService.resetPassword(resetPasswordDTO);

        then(userService).should().updatePassword(anyString(), any(User.class));
        then(userService).shouldHaveNoMoreInteractions();
        then(passwordResetCodeRepository).should().deleteByUser_UserId(anyLong());
        then(passwordResetCodeRepository).shouldHaveNoMoreInteractions();
    }
}