package com.resendegabriel.investwalletapi.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record ActiveTypeRequestDTO(@NotBlank String activeType) {
}
