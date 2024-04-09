package com.resendegabriel.investwalletapi.service;

import com.resendegabriel.investwalletapi.domain.Wallet;
import com.resendegabriel.investwalletapi.domain.dto.request.UpdateWalletDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.WalletRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.WalletActiveSectorsReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.WalletActiveTypesReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.WalletActivesReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.WalletResponseDTO;

import java.util.List;

public interface IWalletService {

    WalletResponseDTO create(WalletRequestDTO walletRequestDTO);

    List<WalletResponseDTO> getAll(Long clientId);

    WalletResponseDTO getById(Long walletId);

    WalletResponseDTO update(Long walletId, UpdateWalletDTO updateWalletDTO);

    void deleteById(Long walletId);

    Wallet findWalletEntityById(Long walletId);

    WalletActivesReportDTO getWalletActivesReport(Long walletId);

    WalletActiveTypesReportDTO getWalletActiveTypesReport(Long walletId);

    WalletActiveSectorsReportDTO getWalletActiveSectorsReport(Long walletId);

    String getWalletOwnerMail(Long walletId);
}
