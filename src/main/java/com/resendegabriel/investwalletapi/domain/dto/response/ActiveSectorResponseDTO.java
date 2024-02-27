package com.resendegabriel.investwalletapi.domain.dto.response;

import com.resendegabriel.investwalletapi.domain.ActiveSector;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActiveSectorResponseDTO(@NotNull Long activeSectorId,

                                      @NotBlank String activeSector) {
    public ActiveSectorResponseDTO(ActiveSector activeSector) {
        this(
                activeSector.getActiveSectorId(),
                activeSector.getActiveSector()
        );
    }
}
