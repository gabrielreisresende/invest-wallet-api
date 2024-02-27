package com.resendegabriel.investwalletapi.service.impl;

import com.resendegabriel.investwalletapi.domain.Active;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveResponseDTO;
import com.resendegabriel.investwalletapi.repository.ActiveRepository;
import com.resendegabriel.investwalletapi.service.IActiveCodeService;
import com.resendegabriel.investwalletapi.service.IActiveService;
import com.resendegabriel.investwalletapi.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActiveService implements IActiveService {

    @Autowired
    private ActiveRepository activeRepository;

    @Autowired
    private IWalletService walletService;

    @Autowired
    private IActiveCodeService activeCodeService;

    @Override
    @Transactional
    public ActiveResponseDTO create(ActiveRequestDTO activeRequestDTO) {
        var wallet = walletService.findWalletEntityById(activeRequestDTO.walletId());
        var activeCode = activeCodeService.findActiveCodeEntity(activeRequestDTO.activeCode());
        var newActive = new Active(activeRequestDTO, wallet, activeCode);
        return new ActiveResponseDTO(activeRepository.save(newActive));
    }
}
