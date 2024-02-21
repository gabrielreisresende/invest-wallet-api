package com.resendegabriel.investwalletapi.domain.dto;

import com.resendegabriel.investwalletapi.domain.ActiveCode;

public record ActiveCodeResponseDTO(String activeCode,

                                    ActiveTypeResponseDTO activeType,

                                    ActiveSectorDTO activeSector) {

    public ActiveCodeResponseDTO(ActiveCode activeCode) {
        this(activeCode.getActiveCode(),
                new ActiveTypeResponseDTO(activeCode.getActiveType()),
                new ActiveSectorDTO(activeCode.getActiveSector()));

    }
}
