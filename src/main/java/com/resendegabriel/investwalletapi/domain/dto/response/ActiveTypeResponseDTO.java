package com.resendegabriel.investwalletapi.domain.dto.response;

import com.resendegabriel.investwalletapi.domain.ActiveType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActiveTypeResponseDTO(@NotNull Long activeTypeId,
                                    @NotBlank String activeType) {

    public ActiveTypeResponseDTO(ActiveType activeType) {
        this(
                activeType.getActiveTypeId(),
                activeType.getActiveType()
        );
    }
}
