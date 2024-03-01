package com.resendegabriel.investwalletapi.service;

import com.resendegabriel.investwalletapi.domain.dto.request.ActiveRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveUpdateDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.ActiveSectorsReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.ActiveTypesReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.ActivesReportDTO;

import java.math.BigDecimal;
import java.util.List;

public interface IActiveService {
    ActiveResponseDTO create(ActiveRequestDTO activeRequestDTO);

    ActiveResponseDTO update(Long activeId, ActiveUpdateDTO activeUpdateDTO);

    void deleteById(Long activeId);

    ActiveResponseDTO getById(Long activeId);

    List<ActivesReportDTO> getActivesReport(Long walletId);

    List<ActiveTypesReportDTO> getActiveTypesReport(Long walletId);

    Integer getDistinctActiveTypesQuantity(Long walletId);

    BigDecimal getWalletTotalValue(Long walletId);

    List<ActiveSectorsReportDTO> getActiveSectorsReport(Long walletId);

    Integer getDistinctActiveSectorsQuantity(Long walletId);
}
