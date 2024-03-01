package com.resendegabriel.investwalletapi.domain.dto.response.reports;

import com.resendegabriel.investwalletapi.domain.dto.response.WalletSimpleDTO;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record WalletActivesReportDTO(WalletSimpleDTO wallet,

                                     BigDecimal walletTotalValue,

                                     List<ActivesReportDTO> actives) {
}
