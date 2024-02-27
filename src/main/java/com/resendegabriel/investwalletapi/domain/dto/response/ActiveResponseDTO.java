package com.resendegabriel.investwalletapi.domain.dto.response;

import com.resendegabriel.investwalletapi.domain.Active;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ActiveResponseDTO(Long activeId,

                                Integer quantity,

                                BigDecimal averageValue,

                                ActiveCodeResponseDTO activeCode,

                                WalletSimpleDTO wallet) {

    public ActiveResponseDTO(Active active) {
        this(
                active.getActiveId(),
                active.getQuantity(),
                active.getAverageValue(),
                new ActiveCodeResponseDTO(active.getActiveCode()),
                new WalletSimpleDTO(active.getWallet())
        );
    }
}
