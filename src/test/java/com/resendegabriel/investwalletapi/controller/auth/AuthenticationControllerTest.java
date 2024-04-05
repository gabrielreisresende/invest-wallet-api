package com.resendegabriel.investwalletapi.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resendegabriel.investwalletapi.domain.auth.dto.EmailDTO;
import com.resendegabriel.investwalletapi.domain.auth.dto.ResetPasswordDTO;
import com.resendegabriel.investwalletapi.domain.auth.dto.TwoFactorCodeDTO;
import com.resendegabriel.investwalletapi.exceptions.ResourceNotFoundException;
import com.resendegabriel.investwalletapi.service.IPasswordResetService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Base64;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private IPasswordResetService passwordResetService;

    private static EmailDTO emailDTO;

    private static TwoFactorCodeDTO twoFactorCodeDTO;

    private static ResetPasswordDTO resetPasswordDTO;

    @BeforeAll
    static void init() {
        emailDTO = new EmailDTO("teste@email.com");
        twoFactorCodeDTO = new TwoFactorCodeDTO(1234, "user@email.com");
        var encryptedPassword = Base64.getEncoder().encode("newPassword".getBytes());
        resetPasswordDTO = new ResetPasswordDTO(Arrays.toString(encryptedPassword), "user@gmail.com");
    }


    @Test
    void shouldReturnCode202WhenGoToForgotPasswordService() throws Exception {
        String json = new ObjectMapper()
                .writeValueAsString(emailDTO);

        mvc.perform(post("/auth/forgot-password")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    void shouldReturnCode202WhenVerifyTwoFactorCode() throws Exception {
        String json = new ObjectMapper()
                .writeValueAsString(twoFactorCodeDTO);

        mvc.perform(post("/auth/verify-code")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    void shouldReturnCode400WhenTryToVerifyTwoFactorCodeWithInvalidCode() throws Exception {
        String json = new ObjectMapper()
                .writeValueAsString(twoFactorCodeDTO);

        doThrow(new ValidationException("Two factor code is not valid")).when(passwordResetService).validateTwoFactorCode(any(TwoFactorCodeDTO.class));

        mvc.perform(post("/auth/verify-code")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation error"))
                .andExpect(jsonPath("$.message").value("Two factor code is not valid"))
                .andExpect(jsonPath("$.path").value("/auth/verify-code"));
    }

    @Test
    void shouldReturnCode200WhenResetPassword() throws Exception {
        String json = new ObjectMapper()
                .writeValueAsString(resetPasswordDTO);

        mvc.perform(put("/auth/reset-password")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnCode404WhenTryToResetPasswordWithInvalidEmail() throws Exception {
        String json = new ObjectMapper()
                .writeValueAsString(resetPasswordDTO);

        doThrow(new ResourceNotFoundException("There is no user with this email. Email user@gmail.com")).when(passwordResetService).resetPassword(any(ResetPasswordDTO.class));

        mvc.perform(put("/auth/reset-password")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Resource Not found"))
                .andExpect(jsonPath("$.message").value("There is no user with this email. Email user@gmail.com"))
                .andExpect(jsonPath("$.path").value("/auth/reset-password"));
    }
}