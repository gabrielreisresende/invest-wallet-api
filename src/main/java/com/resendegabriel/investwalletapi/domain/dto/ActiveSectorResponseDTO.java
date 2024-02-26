package com.resendegabriel.investwalletapi.domain.dto;

import com.resendegabriel.investwalletapi.domain.ActiveSector;

public record ActiveSectorResponseDTO(Long activeSectorId,

                                      String activeSector) {

    public ActiveSectorResponseDTO(ActiveSector activeSector) {
        this(
                activeSector.getActiveSectorId(),
                activeSector.getActiveSector()
        );
    }
}
