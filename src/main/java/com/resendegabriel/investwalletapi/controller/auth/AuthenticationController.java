package com.resendegabriel.investwalletapi.controller.auth;

import com.resendegabriel.investwalletapi.controller.doc.auth.AuthLoginDoc;
import com.resendegabriel.investwalletapi.controller.doc.auth.ForgotPasswordDoc;
import com.resendegabriel.investwalletapi.controller.doc.auth.ResetPasswordDoc;
import com.resendegabriel.investwalletapi.controller.doc.auth.VerifyTwoFactorCodeDoc;
import com.resendegabriel.investwalletapi.domain.auth.User;
import com.resendegabriel.investwalletapi.domain.auth.dto.EmailDTO;
import com.resendegabriel.investwalletapi.domain.auth.dto.LoginResponseDTO;
import com.resendegabriel.investwalletapi.domain.auth.dto.ResetPasswordDTO;
import com.resendegabriel.investwalletapi.domain.auth.dto.TwoFactorCodeDTO;
import com.resendegabriel.investwalletapi.service.IPasswordResetService;
import com.resendegabriel.investwalletapi.service.auth.TokenService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "1. Authentication", description = "Endpoints for authentication process in the application.")
public class AuthenticationController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IPasswordResetService passwordResetService;

    @AuthLoginDoc
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestHeader(value = HttpHeaders.AUTHORIZATION)
                                                  @Schema(hidden = true) String authorization) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (User) authentication.getPrincipal();
        var tokenJWT = tokenService.generateToken(user);
        return ResponseEntity.ok().body(new LoginResponseDTO(tokenJWT, user.getUserId()));
    }

    @ForgotPasswordDoc
    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody @Valid EmailDTO emailDTO) {
        passwordResetService.generatePasswordResetCode(emailDTO);
        return ResponseEntity.accepted().build();
    }

    @VerifyTwoFactorCodeDoc
    @PostMapping("/verify-code")
    public ResponseEntity<Void> verifyTwoFactorCode(@RequestBody @Valid TwoFactorCodeDTO twoFactorCodeDTO) {
        passwordResetService.validateTwoFactorCode(twoFactorCodeDTO);
        return ResponseEntity.accepted().build();
    }

    @ResetPasswordDoc
    @PutMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody @Valid ResetPasswordDTO resetPasswordDTO) {
        passwordResetService.resetPassword(resetPasswordDTO);
        return ResponseEntity.noContent().build();
    }
}