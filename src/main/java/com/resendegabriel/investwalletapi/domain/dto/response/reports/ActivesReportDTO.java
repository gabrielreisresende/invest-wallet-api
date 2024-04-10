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

    private Integer quantity;

    private BigDecimal averageValue;

    private BigDecimal activeTotalValue;

    private BigDecimal activeValuePercentage;

    public ActivesReportDTO(Long activeId, String activeCode, Integer quantity, BigDecimal averageValue, BigDecimal activeTotalValue) {
        this.activeId = activeId;
        this.activeCode = activeCode;
        this.quantity = quantity;
        this.averageValue = averageValue;
        this.activeTotalValue = activeTotalValue.setScale(2, RoundingMode.HALF_UP);
    }
}
