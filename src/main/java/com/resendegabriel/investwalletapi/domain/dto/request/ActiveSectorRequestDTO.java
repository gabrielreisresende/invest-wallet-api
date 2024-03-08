package com.resendegabriel.investwalletapi.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ActiveSectorRequestDTO(@NotBlank String activeSector) {
}
