package com.resendegabriel.investwalletapi.domain.dto;

import com.resendegabriel.investwalletapi.domain.ActiveSector;

public record ActiveSectorDTO(Long activeSectorId,

                              String activeSector) {

    public ActiveSectorDTO(ActiveSector activeSector) {
        this(
                activeSector.getActiveSectorId(),
                activeSector.getActiveSector());
    }
}
