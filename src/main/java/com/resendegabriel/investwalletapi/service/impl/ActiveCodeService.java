package com.resendegabriel.investwalletapi.service.impl;

import com.resendegabriel.investwalletapi.domain.ActiveCode;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveCodeRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveCodeResponseDTO;
import com.resendegabriel.investwalletapi.exceptions.ResourceNotFoundException;
import com.resendegabriel.investwalletapi.repository.ActiveCodeRepository;
import com.resendegabriel.investwalletapi.service.IActiveCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActiveCodeService implements IActiveCodeService {

    @Autowired
    private ActiveCodeRepository activeCodeRepository;

    @Override
    @Transactional
    public ActiveCodeResponseDTO create(ActiveCodeRequestDTO activeCodeRequestDTO) {
        var newActiveCode = new ActiveCode(activeCodeRequestDTO);
        return new ActiveCodeResponseDTO(activeCodeRepository.save(newActiveCode));
    }

    @Override
    public List<ActiveCodeResponseDTO> getAll() {
        return activeCodeRepository.findAll()
                .stream()
                .map(ActiveCodeResponseDTO::new)
                .toList();
    }

    @Override
    public ActiveCodeResponseDTO getByCode(String activeCode) {
        return new ActiveCodeResponseDTO(findActiveCodeEntity(activeCode));
    }

    @Override
    @Transactional
    public void delete(String activeCode) {
        var activeCodeEntity = findActiveCodeEntity(activeCode);
        activeCodeRepository.delete(activeCodeEntity);
    }

    @Override
    public ActiveCode findActiveCodeEntity(String activeCode) {
        return activeCodeRepository.findByActiveCode(activeCode).
                orElseThrow(() -> new ResourceNotFoundException("There is no active code with this code. Code " + activeCode));
    }
}
