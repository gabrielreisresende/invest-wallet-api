package com.resendegabriel.investwalletapi.domain.dto;

import com.resendegabriel.investwalletapi.domain.ActiveType;

public record ActiveTypeDTO(Long activeTypeId,

                            String activeType) {

    public ActiveTypeDTO(ActiveType activeType) {
        this(
                activeType.getActiveTypeId(),
                activeType.getActiveType()
        );
    }
}
