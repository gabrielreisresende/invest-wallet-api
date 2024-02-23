package com.resendegabriel.investwalletapi.service;

import com.resendegabriel.investwalletapi.domain.dto.ActiveTypeResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.ActiveTypeRequestDTO;

public interface IActiveTypeService {

    ActiveTypeResponseDTO create(ActiveTypeRequestDTO activeTypeRequestDTO);

    ActiveTypeResponseDTO updateActiveTypeName(Long activeTypeId, ActiveTypeRequestDTO activeTypeRequestDTO);
}
