package com.resendegabriel.investwalletapi.domain.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record WalletActivesReportDTO(WalletSimpleDTO wallet,

                                     BigDecimal walletTotalValue,

                                     List<ActivesReportDTO> actives) {
}
