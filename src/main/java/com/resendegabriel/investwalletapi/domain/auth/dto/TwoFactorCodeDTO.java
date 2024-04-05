package com.resendegabriel.investwalletapi.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TwoFactorCodeDTO(@NotNull int code,

                               @Email @NotBlank String email) {
}
