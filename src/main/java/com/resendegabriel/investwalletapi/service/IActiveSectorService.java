package com.resendegabriel.investwalletapi.service;

import com.resendegabriel.investwalletapi.domain.dto.request.ActiveSectorRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveSectorResponseDTO;

import java.util.List;

public interface IActiveSectorService {

    ActiveSectorResponseDTO create(ActiveSectorRequestDTO activeSectorRequestDTO);

    ActiveSectorResponseDTO updateActiveSectorName(Long activeTypeId, ActiveSectorRequestDTO activeSectorRequestDTO);

    List<ActiveSectorResponseDTO> getAll();

    ActiveSectorResponseDTO getById(Long activeSectorId);

    void deleteById(Long activeSectorId);
}
