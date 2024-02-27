package com.resendegabriel.investwalletapi.service;

import com.resendegabriel.investwalletapi.domain.dto.request.ActiveRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveUpdateDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveResponseDTO;

public interface IActiveService {
    ActiveResponseDTO create(ActiveRequestDTO activeRequestDTO);

    ActiveResponseDTO update(Long activeId, ActiveUpdateDTO activeUpdateDTO);

    void deleteById(Long activeId);

    ActiveResponseDTO getById(Long activeId);
}
