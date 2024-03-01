package com.resendegabriel.investwalletapi.domain.dto.response.reports;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class ActivesReportDTO {

    private Long activeId;

    private String activeCode;

    private BigDecimal activeTotalValue;

    private BigDecimal activeValuePercentage;

    public ActivesReportDTO(Long activeId, String activeCode, BigDecimal activeTotalValue) {
        this.activeId = activeId;
        this.activeCode = activeCode;
        this.activeTotalValue = activeTotalValue.setScale(2, RoundingMode.HALF_UP);
    }
}
