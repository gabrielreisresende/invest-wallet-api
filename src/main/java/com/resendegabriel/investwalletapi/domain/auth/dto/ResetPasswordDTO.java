package com.resendegabriel.investwalletapi.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResetPasswordDTO(@NotBlank String password,

                               @Email @NotBlank String email) {
}
