package com.resendegabriel.investwalletapi.service;

import com.resendegabriel.investwalletapi.domain.dto.WalletRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.WalletResponseDTO;

public interface IWalletService {

    WalletResponseDTO create(WalletRequestDTO walletRequestDTO);
}
