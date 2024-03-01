package com.resendegabriel.investwalletapi.domain.dto.response.reports;

import com.resendegabriel.investwalletapi.domain.dto.response.WalletSimpleDTO;

import java.math.BigDecimal;
import java.util.List;

public record WalletActiveSectorsReportDTO(WalletSimpleDTO wallet,

                                           BigDecimal walletTotalValue,

                                           Integer distinctActiveSectorsQuantity,

                                           List<ActiveSectorsReportDTO> activeSectors) {
}
