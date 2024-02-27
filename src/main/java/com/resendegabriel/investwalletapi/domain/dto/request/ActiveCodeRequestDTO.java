package com.resendegabriel.investwalletapi.domain.dto.request;

import com.resendegabriel.investwalletapi.domain.dto.response.ActiveSectorResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveTypeResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record ActiveCodeRequestDTO(@NotBlank String activeCode,

                                   @Valid ActiveTypeResponseDTO activeType,

                                   @Valid ActiveSectorResponseDTO activeSector) {
}
