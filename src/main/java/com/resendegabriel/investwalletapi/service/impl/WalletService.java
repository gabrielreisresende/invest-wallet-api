package com.resendegabriel.investwalletapi.service.impl;

import com.resendegabriel.investwalletapi.domain.Wallet;
import com.resendegabriel.investwalletapi.domain.dto.WalletRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.WalletResponseDTO;
import com.resendegabriel.investwalletapi.repository.WalletRepository;
import com.resendegabriel.investwalletapi.service.ICustomerService;
import com.resendegabriel.investwalletapi.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletService implements IWalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private ICustomerService customerService;

    @Override
    @Transactional
    public WalletResponseDTO create(WalletRequestDTO walletRequestDTO) {
        var customer = customerService.findById(walletRequestDTO.customerId());
        var wallet = new Wallet(walletRequestDTO, customer);
        return new WalletResponseDTO(walletRepository.save(wallet));
    }
}
