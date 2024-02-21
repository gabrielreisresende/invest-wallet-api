package com.resendegabriel.investwalletapi.service.impl;

import com.resendegabriel.investwalletapi.domain.ActiveType;
import com.resendegabriel.investwalletapi.domain.dto.ActiveTypeResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.ActiveTypeRequestDTO;
import com.resendegabriel.investwalletapi.repository.ActiveTypeRepository;
import com.resendegabriel.investwalletapi.service.IActiveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActiveTypeService implements IActiveTypeService {

    @Autowired
    private ActiveTypeRepository activeTypeRepository;

    @Override
    @Transactional
    public ActiveTypeResponseDTO create(ActiveTypeRequestDTO activeTypeRequestDTO) {
        return new ActiveTypeResponseDTO(activeTypeRepository.save(new ActiveType(activeTypeRequestDTO)));
    }
}
