package com.resendegabriel.investwalletapi.service;

import com.resendegabriel.investwalletapi.domain.dto.request.ActiveRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveResponseDTO;

public interface IActiveService {
    ActiveResponseDTO create(ActiveRequestDTO activeRequestDTO);
}
