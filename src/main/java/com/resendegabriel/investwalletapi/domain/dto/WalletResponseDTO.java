package com.resendegabriel.investwalletapi.domain.dto;

import com.resendegabriel.investwalletapi.domain.Wallet;

import java.util.List;
import java.util.stream.Collectors;

public record WalletResponseDTO(Long walletId,

                                String name,

                                CustomerResponseDTO customerResponseDTO,

                                List<ActiveResponseDTO> actives) {

    public WalletResponseDTO(Wallet wallet) {
        this(
                wallet.getWalletId(),
                wallet.getName(),
                new CustomerResponseDTO(wallet.getCustomer()),
                wallet.getActives().stream().map(ActiveResponseDTO::new).collect(Collectors.toList())
        );
    }
}
