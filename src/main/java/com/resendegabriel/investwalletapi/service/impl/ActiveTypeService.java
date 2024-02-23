package com.resendegabriel.investwalletapi.service.impl;

import com.resendegabriel.investwalletapi.domain.ActiveType;
import com.resendegabriel.investwalletapi.domain.dto.ActiveTypeRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.ActiveTypeResponseDTO;
import com.resendegabriel.investwalletapi.exceptions.ResourceNotFoundException;
import com.resendegabriel.investwalletapi.repository.ActiveTypeRepository;
import com.resendegabriel.investwalletapi.service.IActiveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActiveTypeService implements IActiveTypeService {

    @Autowired
    private ActiveTypeRepository activeTypeRepository;

    @Override
    @Transactional
    public ActiveTypeResponseDTO create(ActiveTypeRequestDTO activeTypeRequestDTO) {
        return new ActiveTypeResponseDTO(activeTypeRepository.save(new ActiveType(activeTypeRequestDTO)));
    }

    @Override
    @Transactional
    public ActiveTypeResponseDTO updateActiveTypeName(Long activeTypeId, ActiveTypeRequestDTO activeTypeRequestDTO) {
        var activeType = findActiveTypeEntity(activeTypeId);
        activeType.updateActiveTypeName(activeTypeRequestDTO.activeType());
        return new ActiveTypeResponseDTO(activeType);
    }

    private ActiveType findActiveTypeEntity(Long activeTypeId) {
        return activeTypeRepository.findById(activeTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no active type with this id. Id " + activeTypeId));
    }

    @Override
    public List<ActiveTypeResponseDTO> getAll() {
        return activeTypeRepository.findAll()
                .stream()
                .map(ActiveTypeResponseDTO::new)
                .toList();
    }
}
