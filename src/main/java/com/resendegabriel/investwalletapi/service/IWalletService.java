package com.resendegabriel.investwalletapi.service;

import com.resendegabriel.investwalletapi.domain.Wallet;
import com.resendegabriel.investwalletapi.domain.dto.request.UpdateWalletDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.WalletRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.WalletActiveTypesReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.WalletActivesReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.WalletResponseDTO;

import java.util.List;

public interface IWalletService {

    WalletResponseDTO create(WalletRequestDTO walletRequestDTO);

    List<WalletResponseDTO> getAll(Long customerId);

    WalletResponseDTO getById(Long walletId);

    WalletResponseDTO update(Long walletId, UpdateWalletDTO updateWalletDTO);

    void deleteById(Long walletId);

    Wallet findWalletEntityById(Long walletId);

    WalletActivesReportDTO getWalletActivesReport(Long walletId);

    WalletActiveTypesReportDTO getWalletActiveTypesReport(Long walletId);
}
