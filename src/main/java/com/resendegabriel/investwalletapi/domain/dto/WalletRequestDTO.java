package com.resendegabriel.investwalletapi.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record WalletRequestDTO(@NotBlank String name,

                               @NotNull Long customerId) {
}
