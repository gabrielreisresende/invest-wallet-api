package com.resendegabriel.investwalletapi.domain.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ActiveUpdateDTO(@Positive(message = "The active quantity must be positive.")
                              Integer quantity,

                              BigDecimal averageValue) {
}
