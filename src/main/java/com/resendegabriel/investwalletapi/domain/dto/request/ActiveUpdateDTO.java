package com.resendegabriel.investwalletapi.domain.dto.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ActiveUpdateDTO(Integer quantity,

                              BigDecimal averageValue) {
}
