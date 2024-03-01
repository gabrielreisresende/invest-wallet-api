package com.resendegabriel.investwalletapi.domain.dto.response.reports;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActiveTypesReportDTO {

    private Long activeTypeId;

    private String activeType;

    private Integer quantityOfActives;

    private BigDecimal quantityPercentage;

    private BigDecimal totalValue;

    private BigDecimal monetaryPercentage;

    public ActiveTypesReportDTO(Long activeTypeId, String activeType, Long quantityOfActives, BigDecimal totalValue) {
        this.activeTypeId = activeTypeId;
        this.activeType = activeType;
        this.quantityOfActives = Math.toIntExact(quantityOfActives);
        this.totalValue = totalValue.setScale(2, RoundingMode.HALF_UP);
    }
}
