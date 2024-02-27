package com.resendegabriel.investwalletapi.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ActiveCodeUpdateDTO(@NotBlank String newActiveCode) {
}
