package com.resendegabriel.investwalletapi.service.impl;

import com.resendegabriel.investwalletapi.domain.Active;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveUpdateDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.ActiveSectorsReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.ActiveTypesReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.ActivesReportDTO;
import com.resendegabriel.investwalletapi.exceptions.ResourceNotFoundException;
import com.resendegabriel.investwalletapi.repository.ActiveRepository;
import com.resendegabriel.investwalletapi.service.IActiveCodeService;
import com.resendegabriel.investwalletapi.service.IActiveService;
import com.resendegabriel.investwalletapi.service.IWalletService;
import com.resendegabriel.investwalletapi.utils.ActiveReportUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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
        return activeRepository.save(newActive).toDto();
    }

    @Override
    @Transactional
    public ActiveResponseDTO update(Long activeId, ActiveUpdateDTO activeUpdateDTO) {
        var active = findAnActiveEntityById(activeId);
        active.updateData(activeUpdateDTO);
        return active.toDto();
    }

    @Override
    @Transactional
    public void deleteById(Long activeId) {
        findAnActiveEntityById(activeId);
        activeRepository.deleteById(activeId);
    }

    @Override
    public ActiveResponseDTO getById(Long activeId) {
        return findAnActiveEntityById(activeId).toDto();
    }

    private Active findAnActiveEntityById(Long activeId) {
        return activeRepository.findById(activeId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no active with this id. Id " + activeId));
    }

    @Override
    public List<ActivesReportDTO> getActivesReport(Long walletId) {
        List<ActivesReportDTO> activesReportDTO = activeRepository.getActivesReport(walletId);

        ActiveReportUtility.setEachActivePercentage(activesReportDTO, getWalletTotalValue(walletId));

        return activesReportDTO;
    }

    @Override
    public List<ActiveTypesReportDTO> getActiveTypesReport(Long walletId) {
        var activeTypesReportDTO = activeRepository.getActiveTypesReport(walletId);

        ActiveReportUtility.setEachActiveTypeQuantityPercentage(activeTypesReportDTO);
        ActiveReportUtility.setEachActiveTypeMonetaryPercentage(activeTypesReportDTO, getWalletTotalValue(walletId));

        return activeTypesReportDTO;
    }

    @Override
    public List<ActiveSectorsReportDTO> getActiveSectorsReport(Long walletId) {
        var activeSectorsReportDTO = activeRepository.getActiveSectorsReport(walletId);

        ActiveReportUtility.setEachActiveSectorQuantityPercentage(activeSectorsReportDTO);
        ActiveReportUtility.setEachActiveSectorMonetaryPercentage(activeSectorsReportDTO, getWalletTotalValue(walletId));

        return activeSectorsReportDTO;
    }

    @Override
    public BigDecimal getWalletTotalValue(Long walletId) {
        return activeRepository.getWalletTotalValue(walletId);
    }

    @Override
    public Integer getDistinctActiveTypesQuantity(Long walletId) {
        return activeRepository.getDistinctActiveTypesQuantity(walletId);
    }

    @Override
    public Integer getDistinctActiveSectorsQuantity(Long walletId) {
        return activeRepository.getDistinctActiveSectorsQuantity(walletId);
    }
}
