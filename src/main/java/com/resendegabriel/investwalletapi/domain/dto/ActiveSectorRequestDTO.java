package com.resendegabriel.investwalletapi.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record ActiveSectorRequestDTO(@NotBlank String activeSector) {
}
