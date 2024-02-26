package com.resendegabriel.investwalletapi.domain.dto.response;

import com.resendegabriel.investwalletapi.domain.ActiveType;

public record ActiveTypeResponseDTO(Long activeTypeId,

                                    String activeType) {

    public ActiveTypeResponseDTO(ActiveType activeType) {
        this(
                activeType.getActiveTypeId(),
                activeType.getActiveType()
        );
    }
}
