package com.resendegabriel.investwalletapi.domain.dto;

import com.resendegabriel.investwalletapi.domain.Active;
import com.resendegabriel.investwalletapi.domain.ActiveCode;

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
