package com.resendegabriel.investwalletapi.domain.dto.response.reports;

import com.resendegabriel.investwalletapi.domain.dto.response.WalletSimpleDTO;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record WalletActiveSectorsReportDTO(WalletSimpleDTO wallet,

                                           BigDecimal walletTotalValue,

                                           Integer distinctActiveSectorsQuantity,

                                           List<ActiveSectorsReportDTO> activeSectors) {
}
