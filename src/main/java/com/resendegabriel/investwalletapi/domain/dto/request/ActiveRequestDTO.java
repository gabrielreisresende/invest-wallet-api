package com.resendegabriel.investwalletapi.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ActiveRequestDTO(@NotNull @Positive(message = "The active quantity must be positive")
                               int quantity,

                               @NotNull BigDecimal averageValue,

                               @NotBlank String activeCode,

                               @NotNull Long walletId) {
}
