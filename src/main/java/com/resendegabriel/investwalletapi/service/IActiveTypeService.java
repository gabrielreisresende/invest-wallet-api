package com.resendegabriel.investwalletapi.service;

import com.resendegabriel.investwalletapi.domain.dto.request.ActiveTypeRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveTypeResponseDTO;

import java.util.List;

public interface IActiveTypeService {

    ActiveTypeResponseDTO create(ActiveTypeRequestDTO activeTypeRequestDTO);

    ActiveTypeResponseDTO updateActiveTypeName(Long activeTypeId, ActiveTypeRequestDTO activeTypeRequestDTO);

    List<ActiveTypeResponseDTO> getAll();

    ActiveTypeResponseDTO getById(Long activeTypeId);

    void deleteById(Long activeTypeId);
}
