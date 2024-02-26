package com.resendegabriel.investwalletapi.domain.dto.response;

import com.resendegabriel.investwalletapi.domain.Active;

import java.math.BigDecimal;

public record ActiveResponseDTO(Long activeId,

                                Integer quantity,

                                BigDecimal averageValue,

                                ActiveCodeResponseDTO activeCode,

                                WalletResponseDTO wallet) {

    public ActiveResponseDTO(Active active){
        this(
                active.getActiveId(),
                active.getQuantity(),
                active.getAverageValue(),
                new ActiveCodeResponseDTO(active.getActiveCode()),
                new WalletResponseDTO(active.getWallet())
        );
    }
}
