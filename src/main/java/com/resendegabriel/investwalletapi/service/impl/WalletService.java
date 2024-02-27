package com.resendegabriel.investwalletapi.service.impl;

import com.resendegabriel.investwalletapi.domain.Wallet;
import com.resendegabriel.investwalletapi.domain.dto.request.UpdateWalletDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.WalletRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.WalletResponseDTO;
import com.resendegabriel.investwalletapi.exceptions.ResourceNotFoundException;
import com.resendegabriel.investwalletapi.repository.WalletRepository;
import com.resendegabriel.investwalletapi.service.ICustomerService;
import com.resendegabriel.investwalletapi.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public List<WalletResponseDTO> getAll(Long customerId) {
        customerService.findById(customerId);
        return walletRepository
                .findAllByCustomer_CustomerId(customerId)
                .stream().map(WalletResponseDTO::new)
                .toList();
    }

    @Override
    public WalletResponseDTO getById(Long walletId) {
        return new WalletResponseDTO(walletRepository.findById(walletId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no wallet with this id. Id " + walletId)));
    }

    @Override
    @Transactional
    public WalletResponseDTO update(Long walletId, UpdateWalletDTO updateWalletDTO) {
        var wallet = findWalletEntityById(walletId);
        wallet.updateName(updateWalletDTO);
        return new WalletResponseDTO(wallet);
    }

    @Override
    @Transactional
    public void deleteById(Long walletId) {
        findWalletEntityById(walletId);
        walletRepository.deleteById(walletId);
    }

    @Override
    public Wallet findWalletEntityById(Long walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no wallet with this id. Id " + walletId));
    }
}
