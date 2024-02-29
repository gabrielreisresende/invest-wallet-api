package com.resendegabriel.investwalletapi.service.impl;

import com.resendegabriel.investwalletapi.domain.Active;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveUpdateDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveTypesReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActivesReportDTO;
import com.resendegabriel.investwalletapi.exceptions.ResourceNotFoundException;
import com.resendegabriel.investwalletapi.repository.ActiveRepository;
import com.resendegabriel.investwalletapi.service.IActiveCodeService;
import com.resendegabriel.investwalletapi.service.IActiveService;
import com.resendegabriel.investwalletapi.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        return new ActiveResponseDTO(activeRepository.save(newActive));
    }

    @Override
    @Transactional
    public ActiveResponseDTO update(Long activeId, ActiveUpdateDTO activeUpdateDTO) {
        var active = findAnActiveEntityById(activeId);
        active.updateData(activeUpdateDTO);
        return new ActiveResponseDTO(active);
    }

    @Override
    @Transactional
    public void deleteById(Long activeId) {
        findAnActiveEntityById(activeId);
        activeRepository.deleteById(activeId);
    }

    @Override
    public ActiveResponseDTO getById(Long activeId) {
        return new ActiveResponseDTO(findAnActiveEntityById(activeId));
    }

    @Override
    public List<ActivesReportDTO> getActivesReport(Long walletId) {
        List<ActivesReportDTO> activesReportDTO = activeRepository.getActivesReport(walletId);

        var walletTotalValue = getWalletTotalValue(activesReportDTO);
        setEachActivePercent(activesReportDTO, walletTotalValue);

        return activesReportDTO;
    }

    @Override
    public BigDecimal getWalletTotalValue(List<ActivesReportDTO> activesReportDTO) {
        return activesReportDTO.stream()
                .map(ActivesReportDTO::getActiveTotalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void setEachActivePercent(List<ActivesReportDTO> activesReportDTO, BigDecimal walletTotalValue) {
        activesReportDTO.forEach(active ->
                active.setActiveValuePercentage(
                        active.getActiveTotalValue()
                                .multiply(new BigDecimal("100"))
                                .divide(walletTotalValue, 2, RoundingMode.HALF_UP)));
    }

    @Override
    public List<ActiveTypesReportDTO> getActiveTypesReport(Long walletId) {
        var activeTypesReportDTO = activeRepository.getActiveTypesReport(walletId);

        setEachActiveTypeQuantityPercent(activeTypesReportDTO);
        setEachActiveTypeMonetaryPercent(walletId, activeTypesReportDTO);

        return activeTypesReportDTO;
    }

    private void setEachActiveTypeMonetaryPercent(Long walletId, List<ActiveTypesReportDTO> activeTypesReportDTO) {
        var walletTotalValue = getWalletTotalValue(walletId);

        activeTypesReportDTO.forEach(activeType ->
                activeType.setMonetaryPercentage(activeType
                        .getTotalValue()
                        .multiply(new BigDecimal("100.00"))
                        .setScale(2, RoundingMode.HALF_UP)
                        .divide(walletTotalValue, RoundingMode.HALF_UP)));
    }

    private void setEachActiveTypeQuantityPercent(List<ActiveTypesReportDTO> activeTypesReportDTO) {
        var totalActiveTypesQuantity = getTotalActiveTypesQuantity(activeTypesReportDTO);

        activeTypesReportDTO.forEach(activeType ->
                activeType.setQuantityPercentage(new BigDecimal(
                        (double) activeType.getQuantityOfActives() * 100 / totalActiveTypesQuantity)
                        .setScale(2, RoundingMode.HALF_UP)));
    }

    private Integer getTotalActiveTypesQuantity(List<ActiveTypesReportDTO> activeTypesReportDTO) {
        return activeTypesReportDTO.stream()
                .mapToInt(ActiveTypesReportDTO::getQuantityOfActives)
                .sum();
    }

    @Override
    public Integer getDistinctActiveTypesQuantity(Long walletId) {
        return activeRepository.getDistinctActiveTypesQuantity(walletId);
    }

    @Override
    public BigDecimal getWalletTotalValue(Long walletId) {
        return activeRepository.getWalletTotalValue(walletId);
    }

    private Active findAnActiveEntityById(Long activeId) {
        return activeRepository.findById(activeId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no active with this id. Id " + activeId));
    }
}
