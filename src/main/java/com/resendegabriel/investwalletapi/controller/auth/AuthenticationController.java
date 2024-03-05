package com.resendegabriel.investwalletapi.controller.auth;

import com.resendegabriel.investwalletapi.controller.doc.auth.AuthLoginDoc;
import com.resendegabriel.investwalletapi.domain.auth.User;
import com.resendegabriel.investwalletapi.domain.auth.dto.LoginResponseDTO;
import com.resendegabriel.investwalletapi.service.auth.TokenService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Login", description = "Endpoint for the user log in the application.")
public class AuthenticationController {

    @Autowired
    private TokenService tokenService;

    @AuthLoginDoc
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestHeader(value = HttpHeaders.AUTHORIZATION)
                                                  @Schema(hidden = true) String authorization) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (User) authentication.getPrincipal();
        var tokenJWT = tokenService.generateToken(user);
        return ResponseEntity.ok().body(new LoginResponseDTO(tokenJWT, user.getUserId()));
    }
}