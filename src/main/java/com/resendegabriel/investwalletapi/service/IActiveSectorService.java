package com.resendegabriel.investwalletapi.service;

import com.resendegabriel.investwalletapi.domain.dto.ActiveSectorRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.ActiveSectorResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.ActiveTypeRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.ActiveTypeResponseDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface IActiveSectorService {

    ActiveSectorResponseDTO create(ActiveSectorRequestDTO activeSectorRequestDTO);

    ActiveSectorResponseDTO updateActiveSectorName(Long activeTypeId, ActiveSectorRequestDTO activeSectorRequestDTO);

    List<ActiveSectorResponseDTO> getAll();

    ActiveSectorResponseDTO getById(Long activeSectorId);

    void deleteById(Long activeSectorId);
}
