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
import com.resendegabriel.investwalletapi.service.ICustomerService;
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
    private ICustomerService customerService;

    @Autowired
    private IActiveService activeService;

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

    @Override
    public WalletActivesReportDTO getWalletActivesReport(Long walletId) {
        var wallet = new WalletSimpleDTO(findWalletEntityById(walletId));

        List<ActivesReportDTO> activesReportDTO = activeService.getActivesReport(walletId);
        var walletTotalValue = activeService.getWalletTotalValue(walletId);

        return new WalletActivesReportDTO(wallet, walletTotalValue, activesReportDTO);
    }

    @Override
    public WalletActiveTypesReportDTO getWalletActiveTypesReport(Long walletId) {
        var wallet = new WalletSimpleDTO(findWalletEntityById(walletId));

        Integer distinctActiveTypesQuantity = activeService.getDistinctActiveTypesQuantity(walletId);
        BigDecimal walletTotalValue = getWalletTotalValue(walletId);

        List<ActiveTypesReportDTO> activeTypesReportDTOS = activeService.getActiveTypesReport(walletId);

        return new WalletActiveTypesReportDTO(wallet, walletTotalValue, distinctActiveTypesQuantity, activeTypesReportDTOS);
    }

    @Override
    public WalletActiveSectorsReportDTO getWalletActiveSectorsReport(Long walletId) {
        var wallet = new WalletSimpleDTO(findWalletEntityById(walletId));

        Integer distinctActiveSectorsQuantity = activeService.getDistinctActiveSectorsQuantity(walletId);
        BigDecimal walletTotalValue = getWalletTotalValue(walletId);

        List<ActiveSectorsReportDTO> activeSectorsReportDTOS = activeService.getActiveSectorsReport(walletId);

        return new WalletActiveSectorsReportDTO(wallet, walletTotalValue, distinctActiveSectorsQuantity, activeSectorsReportDTOS);
    }

    private BigDecimal getWalletTotalValue(Long walletId) {
        return activeService.getWalletTotalValue(walletId);
    }
}
