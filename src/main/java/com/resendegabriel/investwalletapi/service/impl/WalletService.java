package com.resendegabriel.investwalletapi.service.impl;

import com.resendegabriel.investwalletapi.domain.Wallet;
import com.resendegabriel.investwalletapi.domain.dto.request.UpdateWalletDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.WalletRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.WalletResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.WalletSimpleDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.ActiveSectorsReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.ActiveTypesReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.ActivesReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.WalletActiveSectorsReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.WalletActiveTypesReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.WalletActivesReportDTO;
import com.resendegabriel.investwalletapi.exceptions.ResourceNotFoundException;
import com.resendegabriel.investwalletapi.repository.WalletRepository;
import com.resendegabriel.investwalletapi.service.IActiveService;
import com.resendegabriel.investwalletapi.service.IClientService;
import com.resendegabriel.investwalletapi.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletService implements IWalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private IClientService clientService;

    @Autowired
    private IActiveService activeService;

    @Override
    @Transactional
    public WalletResponseDTO create(WalletRequestDTO walletRequestDTO) {
        var client = clientService.findById(walletRequestDTO.clientId());
        var wallet = new Wallet(walletRequestDTO, client);
        return walletRepository.save(wallet).toWalletResponseDto();
    }

    @Override
    public List<WalletResponseDTO> getAll(Long clientId) {
        clientService.findById(clientId);
        return walletRepository
                .findAllByClient_ClientId(clientId)
                .stream().map(WalletResponseDTO::new)
                .toList();
    }

    @Override
    public WalletResponseDTO getById(Long walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no wallet with this id. Id " + walletId)).toWalletResponseDto();
    }

    @Override
    @Transactional
    public WalletResponseDTO update(Long walletId, UpdateWalletDTO updateWalletDTO) {
        var wallet = findWalletEntityById(walletId);
        wallet.updateName(updateWalletDTO);
        return wallet.toWalletResponseDto();
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

    @Override
    public WalletActivesReportDTO getWalletActivesReport(Long walletId) {
        var wallet = createWalletSimpleResponse(walletId);

        List<ActivesReportDTO> activesReportDTO = activeService.getActivesReport(walletId);
        var walletTotalValue = activeService.getWalletTotalValue(walletId);

        return new WalletActivesReportDTO(wallet, walletTotalValue, activesReportDTO);
    }

    @Override
    public WalletActiveTypesReportDTO getWalletActiveTypesReport(Long walletId) {
        var wallet = createWalletSimpleResponse(walletId);

        Integer distinctActiveTypesQuantity = activeService.getDistinctActiveTypesQuantity(walletId);
        BigDecimal walletTotalValue = getWalletTotalValue(walletId);

        List<ActiveTypesReportDTO> activeTypesReportDTOS = activeService.getActiveTypesReport(walletId);

        return new WalletActiveTypesReportDTO(wallet, walletTotalValue, distinctActiveTypesQuantity, activeTypesReportDTOS);
    }

    @Override
    public WalletActiveSectorsReportDTO getWalletActiveSectorsReport(Long walletId) {
        var wallet = createWalletSimpleResponse(walletId);

        Integer distinctActiveSectorsQuantity = activeService.getDistinctActiveSectorsQuantity(walletId);
        BigDecimal walletTotalValue = getWalletTotalValue(walletId);

        List<ActiveSectorsReportDTO> activeSectorsReportDTOS = activeService.getActiveSectorsReport(walletId);

        return new WalletActiveSectorsReportDTO(wallet, walletTotalValue, distinctActiveSectorsQuantity, activeSectorsReportDTOS);
    }

    @Override
    public String getWalletOwnerMail(Long walletId) {
        return walletRepository.findWalletOwnerMail(walletId);
    }

    private BigDecimal getWalletTotalValue(Long walletId) {
        return activeService.getWalletTotalValue(walletId);
    }

    private WalletSimpleDTO createWalletSimpleResponse(Long walletId) {
        return findWalletEntityById(walletId).toWalletSimpleDto();
    }
}
