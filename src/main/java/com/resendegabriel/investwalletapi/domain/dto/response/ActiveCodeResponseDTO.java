package com.resendegabriel.investwalletapi.domain.dto.response;

import com.resendegabriel.investwalletapi.domain.ActiveCode;
import lombok.Builder;

@Builder
public record ActiveCodeResponseDTO(String activeCode,

                                    ActiveTypeResponseDTO activeType,

                                    ActiveSectorResponseDTO activeSector) {

    public ActiveCodeResponseDTO(ActiveCode activeCode) {
        this(
                activeCode.getActiveCode(),
                new ActiveTypeResponseDTO(activeCode.getActiveType()),
                new ActiveSectorResponseDTO(activeCode.getActiveSector())
        );
    }
}
