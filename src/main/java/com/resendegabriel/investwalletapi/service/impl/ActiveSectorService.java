package com.resendegabriel.investwalletapi.service.impl;

import com.resendegabriel.investwalletapi.domain.ActiveSector;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveSectorRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveSectorResponseDTO;
import com.resendegabriel.investwalletapi.exceptions.ResourceNotFoundException;
import com.resendegabriel.investwalletapi.repository.ActiveSectorRepository;
import com.resendegabriel.investwalletapi.service.IActiveSectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActiveSectorService implements IActiveSectorService {

    @Autowired
    private ActiveSectorRepository activeSectorRepository;

    @Override
    @Transactional
    public ActiveSectorResponseDTO create(ActiveSectorRequestDTO activeSectorRequestDTO) {
        return new ActiveSectorResponseDTO(activeSectorRepository.save(new ActiveSector(activeSectorRequestDTO)));
    }

    @Override
    @Transactional
    public ActiveSectorResponseDTO updateActiveSectorName(Long activeSectorId, ActiveSectorRequestDTO activeSectorRequestDTO) {
        var activeSector = findActiveSectorEntity(activeSectorId);
        activeSector.updateActiveSectorName(activeSectorRequestDTO.activeSector());
        return new ActiveSectorResponseDTO(activeSector);
    }

    private ActiveSector findActiveSectorEntity(Long activeSectorId) {
        return activeSectorRepository.findById(activeSectorId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no active sector with this id. Id " + activeSectorId));
    }

    @Override
    public List<ActiveSectorResponseDTO> getAll() {
        return activeSectorRepository.findAll()
                .stream()
                .map(ActiveSectorResponseDTO::new)
                .toList();
    }

    @Override
    public ActiveSectorResponseDTO getById(Long activeSectorId) {
        return new ActiveSectorResponseDTO(findActiveSectorEntity(activeSectorId));
    }

    @Override
    @Transactional
    public void deleteById(Long activeSectorId) {
        findActiveSectorEntity(activeSectorId);
        activeSectorRepository.deleteById(activeSectorId);
    }
}
