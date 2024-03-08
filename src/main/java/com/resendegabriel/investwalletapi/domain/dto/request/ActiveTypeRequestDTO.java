package com.resendegabriel.investwalletapi.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ActiveTypeRequestDTO(@NotBlank String activeType) {
}
