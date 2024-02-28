package com.resendegabriel.investwalletapi.service;

import com.resendegabriel.investwalletapi.domain.dto.request.ActiveRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveUpdateDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActivesReportDTO;

import java.math.BigDecimal;
import java.util.List;

public interface IActiveService {
    ActiveResponseDTO create(ActiveRequestDTO activeRequestDTO);

    ActiveResponseDTO update(Long activeId, ActiveUpdateDTO activeUpdateDTO);

    void deleteById(Long activeId);

    ActiveResponseDTO getById(Long activeId);

    List<ActivesReportDTO> getActivesReport(Long walletId);

    BigDecimal getWalletTotalValue(List<ActivesReportDTO> activesReportDTO);
}
