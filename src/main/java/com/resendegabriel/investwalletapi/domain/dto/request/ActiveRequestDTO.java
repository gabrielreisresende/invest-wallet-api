package com.resendegabriel.investwalletapi.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ActiveRequestDTO(@NotNull int quantity,

                               @NotNull BigDecimal averageValue,

                               @NotBlank String activeCode,

                               @NotNull Long walletId) {
}
